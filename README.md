# edgrab

Simple Clojure library to grab XML-based XBRL files from EDGAR

## Usage

1. Add to your project file's dependencies
  ``` clj
  [edgrab "1.0.1"]
  ```
2. Call the library in your REPL or file
  ``` clj
  (use 'edgrab.core)
  ```
3. Call the function grab-filing to return parsed XML data given a company's ticker, the filing type, and (optionally), the prior to date in YYYYMMDD format.
  ```clj
  (grab-filing "aapl" "10-k" "20120101")
  ```

## License

Copyright © 2014 Nikola Peric

Distributed under the Eclipse Public License version 1.0.
