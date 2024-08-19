-- Solutions to SQL questions

--- Questions 1: Insert some data into a table


INSERT INTO cd.facilities
VALUES
  (9, 'Spa', 20, 30, 100000, 800);

--- Question 2: Insert calculated data into a table


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

--- Question 3: Update some existing data


UPDATE
  cd.facilities
SET
  initialoutlay = 10000
WHERE
  facid = 1;

--- Question 4: Update a row based on the contents of another row

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

--- Question 5: Delete all bookings

DELETE FROM cd.bookings;

--- Question 6: Delete a member from the cd.members table


DELETE FROM
  cd.members
WHERE
  memid = 37;


-- Basics

--- Question 7: Control which rows are retrieved - part 2

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

--- Question 8: Basic string searches

SELECT
  *
FROM
  cd.facilities
WHERE
  name LIKE '%Tennis%';

--- Question 9: Matching against multiple possible values

SELECT
  *
FROM
  cd.facilities
WHERE
  facid IN (1, 5);

-- Question 10: Working with dates

SELECT
  memid,
  surname,
  firstname,
  joindate
FROM
  cd.members
WHERE
  joindate > '2012-09-01';

--- Question 11: Combining results from multiple queries

SELECT
  surname
FROM
  cd.members
UNION
SELECT
  name
FROM
  cd.facilities;

-- Join

--- Question 12: Retrieve the start times of members' bookings

SELECT
  starttime
FROM
  cd.bookings
  JOIN cd.members on cd.bookings.memid = cd.members.memid
WHERE
  cd.members.firstname = 'David'
  AND surname = 'Farrell';


--- Question 13: Work out the start times of bookings for tennis courts

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

--- Question 14: Produce a list of all members, along with their recommender

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

--- Question 15: Produce a list of all members, along with their recommender

SELECT
  DISTINCT r.firstname AS firstname,
  r.surname AS surname
FROM
  cd.members AS m
  JOIN cd.members AS r ON m.recommendedby = r.memid
ORDER BY
  surname,
  firstname;

--- Question 16: Produce a list of all members, along with their recommender, using no joins.

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

-- Aggregation

--- Question 17: Count the number of recommendations each member makes.

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

--- Question 18: List the total slots booked per facility

SELECT
  facid,
  SUM(slots) AS "Total Slots"
FROM
  cd.bookings
GROUP BY
  facid
ORDER BY
  facid;

--- Question 19: List the total slots booked per facility in a given month

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

--- Question 20: List the total slots booked per facility per month

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

--- Question 21: Find the count of members who have made at least one booking


SELECT
  COUNT(DISTINCT memid)
FROM
  cd.bookings;

--- Question 22: List each member's first booking after September 1st 2012

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

--- Question 23: Produce a list of member names, with each row containing the total member count

SELECT
  COUNT(*) OVER(),
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;

--- Question 24: Produce a numbered list of members

SELECT
  ROW_NUMBER() OVER(),
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;

--- Question 25: Output the facility id that has the highest number of slots booked, again

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

-- String

--- Question 26: Format the names of members

SELECT
  surname || ', ' || firstname AS name
FROM
  cd.members;

--- Question 27: Find telephone numbers with parentheses

SELECT
  memid,
  telephone
FROM
  cd.members
WHERE
  telephone ~ '[()]';

--- Question 28: Count the number of members whose surname starts with each letter of the alphabet

SELECT
  SUBSTRING(surname, 1, 1) AS letter,
  COUNT(surname)
FROM
  cd.members
GROUP BY
  letter
ORDER BY
  letter;
