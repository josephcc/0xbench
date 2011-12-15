
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <netinet/tcp.h>


#define SERVERHOST      "localhost"
#define BUFFER_SIZE     64
uint16_t PORT = -1;
int SOCK = -1;
int socket_on = 0;

void init_sockaddr (struct sockaddr_in *name, const char *hostname, uint16_t port)
{
    struct hostent *hostinfo;
 
    name->sin_family = AF_INET;
    name->sin_port = htons (port);
    hostinfo = gethostbyname (hostname);
    if (hostinfo == NULL) {
        fprintf (stderr, "Unknown host %s.\n", hostname);
        fflush(stderr);
        exit (EXIT_FAILURE);
    }
    name->sin_addr = *(struct in_addr *) hostinfo->h_addr;
}

int make_socket()
{
    int sock = socket (PF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        fprintf(stderr, "cannot create socket.\n");
        fflush(stderr);
        exit(EXIT_FAILURE);
    }

    /* Disable Nagle buffering algo */
    int flag = 1;
    setsockopt(sock, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(int));  

    union {
        struct sockaddr_in in;
        struct sockaddr sa;
    } servername;
    /* Connect to the server. */
    init_sockaddr (&servername.in, SERVERHOST, PORT);
    if ( 0 > connect(sock, &servername.sa, sizeof (servername)) ) {
        fprintf (stderr, "cannot connect to server.");
        fflush(stderr);
        exit (EXIT_FAILURE);
    }
    
    return sock;
}

void init_socket()
{
    char *port = getenv("ZXBENCH_PORT");
    if (port != NULL){
        PORT = atoi(port);
        socket_on = 1;
        fprintf(stderr, "got port number from env: %d\n", PORT);
    }
    if(socket_on ==1)
        SOCK = make_socket();
    if(socket_on ==1){
        fprintf(stderr, "socket created\n");
    }
}

void send_socket(char* message)
{
    if(socket_on == 1) 
        write (SOCK, message, strlen(message));
}

void close_socket()
{
    if(socket_on ==1)
        close(SOCK);
}

