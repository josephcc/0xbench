/*******************************************************************************
 *          
 *  The BYTE UNIX Benchmarks - Release 3
 *          Module: timeit.c   SID: 3.3 5/15/91 19:30:21
 *******************************************************************************
 * Bug reports, patches, comments, suggestions should be sent to:
 *
 *	Ben Smith, Rick Grehan or Tom Yager
 *	ben@bytepb.byte.com   rick_g@bytepb.byte.com   tyager@bytepb.byte.com
 *
 *******************************************************************************
 *  Modification Log:
 *  May 12, 1989 - modified empty loops to avoid nullifying by optimizing
 *                 compilers
 *  August 28, 1990 - changed timing relationship--now returns total number
 *	                  of iterations (ty)
 *  October 22, 1997 - code cleanup to remove ANSI C compiler warnings
 *                     Andy Kahn <kahn@zk3.dec.com>
 *
 ******************************************************************************/

/* this module is #included in other modules--no separate SCCS ID */

/*
 *  Timing routine
 *
 */

#include <signal.h>
#include <unistd.h>

#include <sys/time.h>

void wake_me(seconds, start, func)
	int seconds;
    struct timeval *start;
	void (*func)();
{
    if (seconds <= 0) {
        fprintf(stderr, "duration second should be >0, set to 1\n");
        seconds = 1;
    }
	/* set up the signal handler */
	signal(SIGALRM, func);
    /* record the start time */
    gettimeofday(start, NULL);
	/* get the clock running */
	alarm(seconds);
}

