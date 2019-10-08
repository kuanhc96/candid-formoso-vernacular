-- add all your SQL setup statements here.

-- You can assume that the following base table has been created with data loaded for you when we test your submission
-- (you still need to create and populate it in your instance however),
-- although you are free to insert extra ALTER COLUMN ... statements to change the column
-- names / types if you like.

--FLIGHTS (fid int,
--         month_id int,        -- 1-12
--         day_of_month int,    -- 1-31
--         day_of_week_id int,  -- 1-7, 1 = Monday, 2 = Tuesday, etc
--         carrier_id varchar(7),
--         flight_num int,
--         origin_city varchar(34),
--         origin_state varchar(47),
--         dest_city varchar(34),
--         dest_state varchar(46),
--         departure_delay int, -- in mins
--         taxi_out int,        -- in mins
--         arrival_delay int,   -- in mins
--         canceled int,        -- 1 means canceled
--         actual_time int,     -- in mins
--         distance int,        -- in miles
--         capacity int,
--         price int            -- in $
--         )


create table Carriers(
cid varchar(7) PRIMARY KEY,
name varchar(83)
);

create table Months(
mid int PRIMARY KEY,
month varchar(9)
);

create table Weekdays(
did int PRIMARY KEY,
day_of_week varchar(9)
);

create table users(
  userName varchar(20) PRIMARY KEY,
  password varchar(20),
  balance int
);

create table reservation(
  id int PRIMARY KEY,
  username varchar(20) references users(userName),
  numFlights int,
  fid1 int,
  fid2 int,
  price int,
  isPaid int,
  day int
);

/*create table itineraries(
  id identity(1,1) PRIMARY KEY,
  numFlights int,
  flight1 not null Flights,
  flight2 FLights
);
*/