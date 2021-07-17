Feature: This is a feature file

  Scenario: PlaceAI Test Scenario
    #Requests from Chrome
    Given I open browser
    And I navigate to "https://google.com" url using chrome browser
    When matomo added name "google.com" to matomo_log_action table
    And matomo recent "idaction" of "matomo_log_action" equals "idaction_name" of "matomo_log_link_visit_action" action id
    And matomo recent "idvisit" of "matomo_log_link_visit_action" equals "idvisit" of "matomo_log_visit"
    Then I close the browser
    #Requests from API call
    When I send requests to "https://www.fox.com" url using API call
    And matomo added name "fox.com" to matomo_log_action table
    And matomo recent "idaction" of "matomo_log_action" equals "idaction_name" of "matomo_log_link_visit_action" action id
    And matomo recent "idvisit" of "matomo_log_link_visit_action" equals "idvisit" of "matomo_log_visit"
    Then first visit is from "CH" and the second is from "Java"

#SELECT idvisit, visit_last_action_time FROM matomo.matomo_log_visit;
#SELECT idvisit,idaction_name,server_time FROM matomo.matomo_log_link_visit_action;
#SELECT idaction,name FROM matomo.matomo_log_action;
