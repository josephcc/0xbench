
/*******************************************************************************
 *  The BYTE UNIX Benchmarks - Release 3
 *          Module: arith.c   SID: 3.3 5/15/91 19:30:19
 *
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
 *  November 9, 1990 - made changes suggested by Keith Cantrell
 *                        (digi!kcantrel) to defeat optimization
 *                        to non-existence
 *  October 22, 1997 - code cleanup to remove ANSI C compiler warnings
 *                     Andy Kahn <kahn@zk3.dec.com>
 *
 ******************************************************************************/

char SCCSid[] = "@(#) @(#)arith.c:3.3 -- 5/15/91 19:30:19";
/*
 *  arithmetic test
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include "timeit.c"

#include "socket.c"

int dumb_stuff(int);

unsigned long iter;
struct timeval start;

/* this function is called when the alarm expires */
void report()
{
    struct timeval end;
    gettimeofday(&end, NULL);
    double elapse = (end.tv_sec + (end.tv_usec/1000000.0)) - (start.tv_sec + (start.tv_usec/1000000.0));
	fprintf(stderr,"COUNT|%ld|1|lps\n", iter);
    fprintf(stderr, "TIME|%f\n", elapse);
    char buff[BUFFER_SIZE];
    sprintf(buff, "COUNT|%ld|1|lps|%f\n", iter, elapse);
    send_socket(buff);
    close_socket();
	exit(0);
}

int main(argc, argv)
int	argc;
char	*argv[];
{
    init_socket();

	int	duration;
	int result = 0;

	if (argc != 2) {
		printf("Usage: %s duration\n", argv[0]);
        close_socket();
		exit(1);
	}

	duration = atoi(argv[1]);

	/* set up alarm call */
	iter = 0;	/* init iteration count */
	wake_me(duration, &start, report);

	/* this loop will be interrupted by the alarm call */
	while (1)
	{
        /* in switching to time-based (instead of iteration-based),
           the following statement was added. It should not skew
           the timings too much--there was an increment and test
           in the "while" expression above. The only difference is
           that now we're incrementing a long instead of an int.  (ty) */
	++iter;
	/* the loop calls a function to insure that something is done
	   the results of the function are fed back in (just so they
	   they won't be thrown away. A loop with
	   unused assignments may get optimized out of existence */
	result = dumb_stuff(result);
	}
}


/************************** dumb_stuff *******************/
int dumb_stuff(i)
int i;
{
#ifndef arithoh
	datum	x, y, z;
		z = 0;
#endif
		/*
		 *     101
		 * sum       i*i/(i*i-1)
		 *     i=2
		 */
		/* notice that the i value is always reset by the loop */
		for (i=2; i<=101; i++)
			{
#ifndef arithoh
			x = i;
			y = x*x;
			z += y/(y-1);
			}
return(x+y+z);
#else
}
return(0);
#endif
}

