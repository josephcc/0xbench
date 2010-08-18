/*
 * Copyright (C) 2010 0xlab - htpp://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <stdlib.h>
#include <stdio.h>

#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <netinet/tcp.h>

#define MESSAGE         "SOCKET!!\n"
#define SERVERHOST      "localhost"
uint16_t PORT = -1;


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
    PORT = atoi(getenv("ZXBENCH_PORT"));

    int sock = socket (PF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        fprintf(stderr, "cannot create socket.\n");
        fflush(stderr);
        exit(EXIT_FAILURE);
    }

    /* Disable Nagle buffering algo */
    int flag = 1;
    setsockopt(sock, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(int));  

    struct sockaddr_in servername;
    /* Connect to the server. */
    init_sockaddr (&servername, SERVERHOST, PORT);
    if ( 0 > connect(sock, (struct sockaddr *) &servername, sizeof (servername)) ) {
        fprintf (stderr, "cannot connect to server.");
        fflush(stderr);
        exit (EXIT_FAILURE);
    }
    
    return sock;
}

void write_to_server (int socket)
{
    int nbytes;

    nbytes = write (socket, MESSAGE, strlen (MESSAGE));
    if (nbytes < 0) {
        fprintf (stderr, "write failed.");
        fflush(stderr);
        exit (EXIT_FAILURE);
    }
}


int main(int argc, char** argv)
{
    printf("HHHEEELLLLOOOOOOOO\n");

    int sock = make_socket();

    int i;
    for (i=0; i<20; i++) {
        fprintf(stdout, "stdout %d\n", i);
        fflush(stdout);
        fprintf(stderr, "srderr %d\n", i);
        fflush(stderr);
        write_to_server(sock);
        sleep(1);
    }

    close(sock);
    return EXIT_SUCCESS;
}


/* -*- Mode: C; tab-width: 4 -*- */ 
