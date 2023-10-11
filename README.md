# The_Fellas_Ads_Test

4)System has one in memory user with 
{
"username":"Admin",
"password":1111
} as RequestBody
in order to get token in post request "/auth"
then you can test other methods using this token in header "Authorization".

3)To check if message sending to Slack channel works,
you need to replace token in application.properties into yours
you can also replace channel name by default it sends to "general".

5)Create a simple daily task to check if a application is up and running
(DailyTaskManager.class) simply prints out message to console every minute;



