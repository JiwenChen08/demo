1.	
Rate limiter — Design a service to limit the number of requests per user/IP; clarify limits (per second/minute), distributed vs single-node, algorithms (token bucket, leaky bucket, fixed window, sliding window), storage for counters, and failure modes.



"""

So, just to clarify — On which senario do we need a Ratelimiter?

do we want to limit requests `per user, per IP, or per API key`?
And what’s the time window? Like `per second or per minute`?
Also, should this limit be `globally` effective, or `locally` effective?

There are a few common `algorithms`, like 
`fixed window, sliding window, and token bucket.`

"""


"""
For a local ratelimiter, we can just An `AtomicNumber`
For global ratelimiter, we can use `Redis` and `Lua script or Redis transaction`.
"""


There are a few common algorithms —
fixed window, sliding window, and token bucket.

