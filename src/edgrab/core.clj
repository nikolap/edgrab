(ns edgrab.core
  (require [clojure.xml :as xml]))

;; URL variables
(def base-url "http://www.sec.gov")
(def search-url "http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=%s&type=%s&dateb=%s&owner=exclude&count=100")
;; Regex variables
(def doc-page-link-regex #"(?<=a href=\").+(?=\" id=\"documentsbutton\")")
(def xml-link-regex #"(?<=a href=\").+\d.xml(?=\")")

(defn grab-filing [ticker filing-type & [prior-to]]
  "
  Returns parsed data from XML filing from EDGAR. It returns nil if there is either
  an invalid ticker or if there is no XML file.

  Parameters: ticker, filing type, (optional: prior to date (YYYYMMDD format))
  Example usage: (grab-filing \"aapl\" \"10-k\" \"20120101\")
  "
  (let [results-page (slurp (format search-url ticker filing-type prior-to))]
    (if (nil? (re-find #"No matching ticker symbol" results-page))
      nil
      (let [doc-page (slurp (str base-url (re-find doc-page-link-regex results-page)))]
        (if (nil? (re-find #"\.xml" doc-page))
          nil
          (xml/parse (str base-url (re-find xml-link-regex doc-page))))))))
