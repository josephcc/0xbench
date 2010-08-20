/*******************************************************************************
 *  The BYTE UNIX Benchmarks - Release 3
 *          Module: spawn.c   SID: 3.3 5/15/91 19:30:20
 *
 *******************************************************************************
 * Bug reports, patches, comments, suggestions should be sent to:
 *
 *	Ben Smith, Rick Grehan or Tom Yagerat BYTE Magazine
 *	ben@bytepb.byte.com   rick_g@bytepb.byte.com   tyager@bytepb.byte.com
 *
 *******************************************************************************
 *  Modification Log:
 *  $Header: spawn.c,v 3.4 87/06/22 14:32:48 kjmcdonell Beta $
 *  August 29, 1990 - Modified timing routines (ty)
 *  October 22, 1997 - code cleanup to remove ANSI C compiler warnings
 *                     Andy Kahn <kahn@zk3.dec.com>
 *
 ******************************************************************************/
char SCCSid[] = "@(#) @(#)spawn.c:3.3 -- 5/15/91 19:30:20";
/*
 *  Process creation
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "timeit.c"

#include "socket.c"

unsigned long iter;
struct timeval start;

void report()
{
    struct timeval end;
    gettimeofday(&end, NULL);
    double elapse = (end.tv_sec + (end.tv_usec/1000000.0)) - (start.tv_sec + (start.tv_usec/1000000.0));
	fprintf(stderr,"COUNT|%lu|1|lps\n", iter);
    fprintf(stderr, "TIME|%f\n", elapse);
    char buff[BUFFER_SIZE];
    sprintf(buff, "COUNT|%lu|1|lps|%f\n", iter, elapse);
    send_socket(buff);
    close_socket();
	exit(0);
}

int main(argc, argv)
int	argc;
char	*argv[];
{
    init_socket();
	int	slave, duration;
	int	status;

	if (argc != 2) {
		fprintf(stderr,"Usage: %s duration \n", argv[0]);
        close_socket();
		exit(1);
	}

	duration = atoi(argv[1]);

	iter = 0;
    wake_me(duration, &start, report);

	while (1) {
		if ((slave = fork()) == 0) {
			/* slave .. boring */
#if debug
			printf("fork OK\n");
#endif
			/* kill it right away */
            close_socket();
			exit(0);
		} else if (slave < 0) {
			/* woops ... */
			fprintf(stderr,"Fork failed at iteration %lu\n", iter);
			perror("Reason");
            close_socket();
			exit(2);
		} else
			/* master */
			wait(&status);
		if (status != 0) {
			fprintf(stderr,"Bad wait status: 0x%x\n", status);
            close_socket();
			exit(2);
		}
		iter++;
#if debug
		printf("Child %d done.\n", slave);
#endif
		}
}
