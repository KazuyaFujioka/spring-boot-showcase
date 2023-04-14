Feature: 契約状況取得

  Background:
    * url baseUrl
    * def path = '/v1/campaign/ongoing'
    * def storage = 'data/' + env + '/'

  Scenario: 実施中のキャンペーン一覧を取得できる
    Given path path

    When method get
    Then status 200
    And match response == read(storage+'/実施中のキャンペーン一覧レスポンス.json')
