drop db if exists fhmw;
create db fhms;

DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS basket;

CREATE TABLE profile(
	user_id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL, 
	username VARCHAR(25) NOT NULL,  	 
	mobilephone VARCHAR(15),
	email VARCHAR(100) NOT NULL, 
	password VARCHAR(100) NOT NULL,
	approved boolean,
	birthday DATE,
 	PRIMARY KEY(user_id)
);

CREATE TABLE login(
   id INT GENERATED ALWAYS AS IDENTITY,
   username VARCHAR(25) NOT NULL,
   password VARCHAR(25) NOT NULL,
   passphrase VARCHAR(100) NOT NULL,
   PRIMARY KEY(id),
   user_id INT,
   CONSTRAINT fk_profile
      FOREIGN KEY(user_id) 
	  REFERENCES profile(user_id)
 	  ON DELETE CASCADE
);


CREATE TABLE item(
	item_id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(25) NOT NULL, 
	qty INT NOT NULL,
	price DECIMAL(10,3) NOT NULL,		 
	status VARCHAR(10),
	approved_on DATE,
	type VARCHAR(10),
	description VARCHAR(100),  
	PRIMARY KEY(item_id)	
);


CREATE TABLE location(
	location_id INT GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(55) NOT NULL, 
	owner VARCHAR(25) NOT NULL,  	 
	delivery_date DATE,
	item_id INT,
 	PRIMARY KEY(location_id),
	CONSTRAINT fk_item
        	FOREIGN KEY(item_id) 
	  	REFERENCES item(item_id)
);


CREATE TABLE basket(
	b_id INT GENERATED ALWAYS AS IDENTITY,
	purchased_by VARCHAR(100) NOT NULL,
	purchased_date DATE, 
	delivery_date DATE,
	qty INT NOT NULL,
 	PRIMARY KEY(b_id),
	item_id INT,
	user_id INT,
	CONSTRAINT fk_item
        FOREIGN KEY(item_id) 
	  	REFERENCES item(item_id),
	CONSTRAINT fk_user
        	FOREIGN KEY(user_id) 
	  	REFERENCES profile(user_id)
);

INSERT INTO profile(name, username, mobilephone,email, password)
VALUES('John Doe', 'john','(408)-111-1234','john.doe@bluebird.dev', 'johnpassw'),
      ('Jane Doe','jane','(408)-111-1235','jane.doe@bluebird.dev', 'janepassw'),
      ('Anne Kyra','kyra','(408)-111-3335','kyra_j@yahoo.com', 'kyrapassw'),
      ('David Wright','david','(408)-222-1234','david.wright@dolphin.dev', 'davidpassw');