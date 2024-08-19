# Introduction

# SQL Quries

###### Table Setup (DDL)

To start this project, I created the three required tables using the given ERD similar to the one bellow.

https://drive.google.com/file/d/1-qTaF_ih_Uo0IM4Ud0xE7KPHmC9YO7Bf/preview

The implementation to create data modeling of the ERD was designed in the following method:
```
CREATE DATABASE sql_example;

CREATE SCHEMA cd;

SET SCHEMA 'cd';

CREATE TABLE IF NOT EXISTS cd.members
  ( 
     memid		INTEGER NOT NULL, 
     surname      	VARCHAR(200) NOT NULL, 
     firstname       	VARCHAR(200) NOT NULL, 
     address 		VARCHAR(300) NOT NULL, 
     zipcode 		INTEGER NOT NULL, 
     telephone          VARCHAR(20) NOT NULL, 
     recommendedby 	INTEGER NOT NULL, 
     joindate		TIMESTAMP NULL,
     CONSTRAINT members_pk PRIMARY KEY (memid),
     CONSTRAINT members_fk FOREIGN KEY (recommendedby) REFERENCES members(memid)
  );
  
CREATE TABLE IF NOT EXISTS cd.bookings
  ( 
     bookid	INTEGER NOT NULL, 
     facid	INTEGER NOT NULL, 
     memid      INTEGER NOT NULL, 
     starttime 	TIMESTAMP NULL, 
     slots 	INTEGER NOT NULL, 
     CONSTRAINT bookings_pk PRIMARY KEY (bookid),
     CONSTRAINT bookings_members_fk FOREIGN KEY (memid) REFERENCES members(memid),
     CONSTRAINT bookings_facilities_fk FOREIGN KEY (facid) REFERENCES facilities(facid)
  );
  
 
CREATE TABLE IF NOT EXISTS cd.facilities
  ( 
     facid		INTEGER NOT NULL, 
     'name'		VARCHAR(100) NOT NULL, 
     membercost      	NUMERIC NOT NULL, 
     guestcost		NUMERIC NOT NULL, 
     initialoutlay	NUMERIC NOT NULL, 
     monthlymaintenance NUMERIC NOT NULL, 
     CONSTRAINT facilities_pk PRIMARY KEY (facid)
  );
```
To compare with the solution, I had to make sure I have the Postgres running and ready to use. The command to set up the tables and populate sample data into them should be as the one below:
```
psql -h localhost -U <username> -f clubdata.sql -d postgres -x -q
```

## Modifying Data

###### Question 0: Show all members 

```sql
SELECT *
FROM cd.members;
```

###### Questions 1: Insert some data into a table
The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:

facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.
```sql
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```
###### Question 2: Insert calculated data into a table

Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else:

Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.

```sql
INSERT INTO cd.facilities 
VALUES 
  (
    (
      SELECT 
        max(facid) 
      FROM 
        cd.facilities
    ) + 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
  );
```
###### Question 3: Update some existing data
We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.

```sql
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;
```
###### Question 4: Update a row based on the contents of another row
We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.
```sql
UPDATE 
  cd.facilities 
SET 
  membercost = (
    SELECT 
      membercost 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) * 1.1, 
  guestcost = (
    SELECT 
      guestcost 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) * 1.1 
WHERE 
  facid = 1;
```
###### Question 5: Delete all bookings
As part of a clearout of our database, we want to delete all bookings from the cd.bookings table. How can we accomplish this?
```sql
DELETE FROM cd.bookings;
```
###### Question 6: Delete a member from the cd.members table
We want to remove member 37, who has never made a booking, from our database. How can we achieve that?

```sql
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;
```

## Basics

###### Question 7: Control which rows are retrieved - part 2
How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.
```sql
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost < monthlymaintenance / 50 
  AND membercost > 0;
```
###### Question 8: Basic string searches
How can you produce a list of all facilities with the word 'Tennis' in their name?
```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';
```
###### Question 9: Matching against multiple possible values
How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.
```sql
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);
```
###### Question 10: Working with dates
How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.
```sql
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate > '2012-09-01';
```
###### Question 11: Combining results from multiple queries
You, for some reason, want a combined list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!
```sql
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;
```
## Join

###### Question 12: Retrieve the start times of members' bookings
How can you produce a list of the start times for bookings by members named 'David Farrell'?
```sql
SELECT 
  starttime 
FROM 
  cd.bookings 
  JOIN cd.members on cd.bookings.memid = cd.members.memid 
WHERE 
  cd.members.firstname = 'David' 
  AND surname = 'Farrell';

```
###### Question 13: Work out the start times of bookings for tennis courts
How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.
```sql
SELECT 
  starttime AS start, 
  name 
FROM 
  cd.bookings 
  JOIN cd.facilities on cd.bookings.facid = cd.facilities.facid 
WHERE 
  cd.bookings.starttime > '2012-09-21' 
  AND cd.bookings.starttime < '2012-09-22' 
  AND cd.facilities.name LIKE '%Tennis Court%' 
ORDER BY 
  starttime;
```
###### Question 14: Produce a list of all members, along with their recommender
How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).
```sql
SELECT 
  m.firstname AS memfname, 
  m.surname AS memsname, 
  r.firstname AS recfname, 
  r.surname AS recsname 
FROM 
  cd.members AS m 
  LEFT JOIN cd.members AS r ON m.recommendedby = r.memid 
ORDER BY 
  m.surname, m.firstname
```
###### Question 15: Produce a list of all members, along with their recommender
How can you output a list of all members who have recommended another member? Ensure that there are no duplicates in the list, and that results are ordered by (surname, firstname).
```sql
SELECT 
  DISTINCT r.firstname AS firstname, 
  r.surname AS surname 
FROM 
  cd.members AS m 
  JOIN cd.members AS r ON m.recommendedby = r.memid 
ORDER BY 
  surname, 
  firstname;
```
###### Question 16: Produce a list of all members, along with their recommender, using no joins.
How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.
```sql
SELECT 
  DISTINCT CONCAT(m.firstname, ' ', m.surname) AS member, 
  (
    SELECT 
      CONCAT(r.firstname, ' ', r.surname) AS recommender 
    FROM 
      cd.members AS r 
    WHERE 
      m.recommendedby = r.memid
  ) 
FROM 
  cd.members AS m 
ORDER BY 
  member;
```
## Aggregation

###### Question 17: Count the number of recommendations each member makes.
Produce a count of the number of recommendations each member has made. Order by member ID.
```sql
SELECT 
  recommendedby, 
  count(recommendedby) AS count 
FROM 
  cd.members 
GROUP BY 
  recommendedby 
HAVING 
  count(recommendedby) > 0 
ORDER BY 
  recommendedby;
```
###### Question 18: List the total slots booked per facility
Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.
```sql
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;
```
###### Question 19: List the total slots booked per facility in a given month
Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.
```sql
SELECT 
  facid, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012-09-01' 
  AND starttime < '2012-10-01' 
GROUP BY 
  facid 
ORDER BY 
  "Total Slots";
```
###### Question 20: List the total slots booked per facility per month
Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.
```sql
SELECT 
  facid, 
  EXTRACT(
    month 
    FROM 
      starttime
  ) AS month, 
  SUM(slots) AS "Total Slots" 
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012-01-01' 
  AND starttime < '2013-01-01' 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;
```
###### Question 21: Find the count of members who have made at least one booking
Find the total number of members (including guests) who have made at least one booking.

```sql
SELECT 
  COUNT(DISTINCT memid) 
FROM 
  cd.bookings;
```
###### Question 22: List each member's first booking after September 1st 2012
Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.
```sql
SELECT 
  m.surname, 
  m.firstname, 
  b.memid, 
  min(starttime) as starttime 
FROM 
  cd.members m 
  JOIN cd.bookings b on m.memid = b.memid 
WHERE 
  starttime > '2012-09-01' 
GROUP BY 
  b.memid, 
  m.surname, 
  m.firstname 
ORDER BY 
  b.memid;
```
###### Question 23: Produce a list of member names, with each row containing the total member count
Produce a list of member names, with each row containing the total member count. Order by join date, and include guest members.
```sql
SELECT 
  COUNT(*) OVER(), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
```
###### Question 24: Produce a numbered list of members
Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.
```sql
SELECT 
  ROW_NUMBER() OVER(), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;
```
###### Question 25: Output the facility id that has the highest number of slots booked, again
Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.
```sql
SELECT 
  facid, 
  SUM(slots) AS "total" 
FROM 
  cd.bookings 
GROUP BY 
  facid 
HAVING 
  SUM(slots) = (
    SELECT 
      MAX(SUM.total) 
    FROM 
      (
        SELECT 
          SUM(slots) AS "total" 
        FROM 
          cd.bookings 
        GROUP BY 
          facid
      ) AS SUM
  );
```
## String

###### Question 26: Format the names of members
Output the names of all members, formatted as 'Surname, Firstname'
```sql
SELECT 
  surname || ', ' || firstname AS name 
FROM 
  cd.members;
```
###### Question 27: Find telephone numbers with parentheses
You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.
```sql
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone ~ '[()]';
```
###### Question 28: Count the number of members whose surname starts with each letter of the alphabet
You'd like to produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.
```sql
SELECT 
  SUBSTRING(surname, 1, 1) AS letter, 
  COUNT(surname) 
FROM 
  cd.members 
GROUP BY 
  letter 
ORDER BY 
  letter;
```
