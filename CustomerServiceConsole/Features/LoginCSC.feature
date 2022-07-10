Feature: Login to CSC

Scenario: Successful Login with valid credentials
	Given User Launch Chrome browser
	When User Opens URL "http://smp18-auto247:8080/csc/"
	And User enters Username as "consoleuser" and Password as "consoleuser1"
	And Click on SIGNIN
	Then Page Title should be "MSISDN - Customer Service Console"
	And Close Browser
	