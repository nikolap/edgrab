(ns edgrab.core-test
  (:use midje.sweet
        edgrab.core))

;; Apple 10-k search results from before 2012
(def test-search-url "http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=0000320193&type=10-k&dateb=20120101&owner=include&count=100")
;; Apple 10-k doc page for 2013
(def test-doc-url "http://www.sec.gov/Archives/edgar/data/320193/000119312513416534/0001193125-13-416534-index.htm")

(fact "test base url works with format correctly"
      (format search-url "aapl" "10-k" nil) =>
      "http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=aapl&type=10-k&dateb=null&owner=exclude&count=100"
      (format search-url "aapl" "10-k" "20120101") =>
      "http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=aapl&type=10-k&dateb=20120101&owner=exclude&count=100")

(fact "test document url regex finds the first item correctly"
      (re-find doc-page-link-regex (slurp test-search-url)) => "/Archives/edgar/data/320193/000119312511282113/0001193125-11-282113-index.htm")

(fact "test xml url regex finds the url correctly"
      (re-find xml-link-regex (slurp test-doc-url)) => "/Archives/edgar/data/320193/000119312513416534/aapl-20130928.xml")

(fact "test that grab filing returns nil if ticker is incorrect"
      (grab-filing "appl" "10-k") => nil)

(fact "test that grab filing returns nil if there are no XBRL XML files to grab"
      (grab-filing "aapl" "PX14A6G") => nil)

(fact "test that grab filing actually returns something"
      (grab-filing "aapl" "10-k") => truthy)

(fact "test that grab filing returns multiple files, in case of Apple for 2013, 6 files"
      (count (grab-filing "aapl" "10-k")) => 6)
