# Quick simple JSON Fetch
### Task at hand after reading pre-defined JSON file:

***Display this list of items to the user based on the following requirements:***

- Display all the items grouped by "listId"<br>
     - List of "name" values are added to a defined listId TreeMap (to unique listIds keys)
     - Unique listIds are added to another defined listId ArrayList for TreeMap lookup
     - A collapsible list of listId's are populated with the correct list of "name"s
- Sort the results first by "listId" then by "name" when displaying.<br>
     - Use of TreeMap sorts the listId (integer) key in ascending order
     - Items in the TreeMap's values of ArrayList (integer) is sorted before populating to it's listId group
     - The sorted list of listId lookup key will populate each listId's collapsible group in ascending order
- Filter out any items where "name" is blank or null.<br>
     - Empty strings and "null" values are ignored to the ArrayList
     - Metadata provided below the collapsible lists to track total displayed and not displayed of the JSON file
     
# Overview for improvement

- Replace ListView for RecyclerView (performance)
- Break down logic of the MainActivity class to different classes (easability for unit testing)
- ViewModel class implementation (problem of coupling view creation with data retrieval, such as device rotation re-requesting API call)
- Exception handling
