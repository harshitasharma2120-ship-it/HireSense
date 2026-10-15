# HireSense

A web-based job portal built with Java Servlets, JSP and JDBC. Job seekers can register, upload resumes and apply for jobs. Employers can post jobs and view applicants. An admin panel manages the platform.

## Features

- User registration and login (Job Seeker / Employer / Admin)
- Resume upload and download
- Resume parsing using the Affinda API
- Employer dashboard to view applicants
- Job seeker dashboard
- Admin panel
- Email notifications via Gmail SMTP
- Custom error pages

## Tech Stack

- *Backend:* Java (JDK 21), Servlets, JSP, JDBC
- *Frontend:* HTML, CSS, JavaScript
- *Database:* MySQL
- *Server:* Apache Tomcat 10.1
- *IDE:* Eclipse

## Project Structure


HireSense/
├── src/main/java/        # Servlets and utility classes
├── src/main/webapp/      # JSP pages, CSS, WEB-INF/web.xml
├── schema.sql            # Database schema
└── README.md


## Setup

1. Clone the repo:
   
   git clone https://github.com/harshitasharma2120-ship-it/HireSense.git
   
2. Import into Eclipse: *File → Import → Existing Projects* (or Dynamic Web Project).
3. Create the database: open MySQL and run schema.sql.
4. Open src/main/webapp/WEB-INF/web.xml and replace the placeholders:

   | Parameter | Value |
   |---|---|
   | username | Your MySQL username |
   | password | Your MySQL password |
   | mailUser | Your Gmail address |
   | mailPassword | Your Gmail App Password |
   | affindaApiKey | Your Affinda API key |

5. Add the MySQL Connector/J and Jakarta Mail jars to WEB-INF/lib if missing.
6. Add Apache Tomcat 10.1 as the server in Eclipse and run the project: *Run As → Run on Server*.
7. Open http://localhost:8080/HireSense

## Author

*Harshita Sharma*

