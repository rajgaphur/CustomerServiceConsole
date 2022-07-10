@CSC
Feature: MSISDN Login page

Background: Below are the common steps for each scenarios
	Given User Launch Chrome browser
	When User Opens URL "http://smp18-auto247:8080/csc/"
	And User enters Username as "consoleuser" and Password as "consoleuser1"
	And Click on SIGNIN
	Then Page Title should be "MSISDN - Customer Service Console"

@CSC_MSISDN_LOGIN
Scenario: Login to CSC with MSISDN
	When Subscriber enters MSISDN "9998888126"
	And click on Create Session button
	Then Page Title should be "MSISDN - Customer Service Console"
	And List all the Serial Numbers
	And Close Browser
	
@CSC_MSISDN_ENTER
Scenario: Login to CSC with MSISDN
	When Subscriber enters MSISDN "9998888126"
	And Close Browser