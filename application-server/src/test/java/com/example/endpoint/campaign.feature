Feature: 契約状況取得

  Background:
    * url baseUrl
    * def path = '/v1/campaign/'
    * def storage = 'data/' + env + '/'

  Scenario: 実施中のキャンペーン管理番号を指定すると該当のキャンペーンを取得できる
    Given path path + 'dOY0aXcFv3grfpT'
    When method get
    Then status 200
    And match response == read(storage+'/キャンペーンレスポンス.json')

  Scenario: 実施終了しているキャンペーン管理番号を指定した場合も該当のキャンペーンを取得できる
    Given path path + 'wJ6PmEWbwSvxKvG'
    When method get
    Then status 200
    And match response == read(storage+'/終了したキャンペーンレスポンス.json')

  Scenario: 存在しないキャンペーン管理番号を指定した場合キャンペーンを取得できない
    Given path path + 123456789
    When method get
    Then status 404

