Social Media Application

Below are the functionality implemented in this application.

1. createPost(userId, postId, content): Compose a new post.

2. getNewsFeed(userId): Retrieve the 20 most recent post ids in the user's news feed. 
   Each item in the news feed must be posted either by one of the user’s followees or by the user herself. 
   Posts must be ordered by posting time starting from the most recent one.

3. follow(followerId, followeeId): Follower follows a followee.

4. unfollow(followerId, followeeId): Follower unfollows a followee.

Future Scope :

With regards to microservice architecture we can segreagate the responsibilities mentioned in this service into below mentioned components to ditribute the 
traffic and scale the particular application based on the load/use cases.

1. Feed Microservice Service
  Main responsibility of this service would be to handle all the request for creating and fetching the post posted by and user.
  Since these type of applciations are read extensinve hence we can scale this applciation instances and balance the load using api gateway.

2. User Service
  Main responsibility of the service would be top handle all the request related to user operations such as creation/update/delete/follow/unfollow.

3. Api Gateway
  Entry point to the above mentioned services and basic finctionality are as follows : 
    a. Authentication of requests
    b. Request routing (Zuul Routes)
    c. Load balancing (Either Ribbon for client side load balancing or we can have eureka or service discovery)
