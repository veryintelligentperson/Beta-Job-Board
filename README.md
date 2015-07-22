# Beta-Job-Board
I have done this project only to learn more about Spring, Hibernate and other stuff. If you would like to know how to make it work, read below.

## Prerequisites

To make this project work we need to:

* install MySQL (can be any other db, although I have used this one)
* install tomcat 7/8
* add three properties file
	email.properties
	files.properties
	persistence-mysql.properties

## Example of properties

#persistence-mysql.properties
	#jdbc.X
	jdbc.driverClassName = com.mysql.jdbc.Driver
	jdbc.url = jdbc:mysql://localhost:3306/exampledatabase
	jdbc.user = user
	jdbc.pass = password

	#hibernate.X
	hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
	hibernate.show_sql = false
	hibernate.hbm2ddl.auto = create


#files.properties
	files.path = /home/example/path

#email.properties
	email.login = login
	email.password = password
	email.smtp = smtp.gmail.com
	email.address = exampleaddress@gmail.com
	email.domain = http://localhost:8080
