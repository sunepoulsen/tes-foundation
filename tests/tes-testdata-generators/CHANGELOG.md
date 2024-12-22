# Changelog

All notable changes to this project will be documented in this file. The target-audience for this document
is developers and operations.

The changelog-format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Developers should add an entry to "Unreleased work" under the appropriate subsection, describing their work
_prior_ to merging to master. The entry should contain a link to the Jira-story.

Adhere to the following format:
```
- [Name of Jira-story or subtask](link to Jira-story): Description of the completed work`
```
Example-entry:

- [TESFOUND-142](https://sunepoulsen.atlassian.net/browse/TESFOUND-142): Test data generators

For release-dates, use date-format: YYYY-MM-DD

## Unreleased work

### Features

- [TESFOUND-142](https://sunepoulsen.atlassian.net/browse/TESFOUND-142): Test data generators
 
  Add test data generators for: 
  1. Text and password values.
  2. Numeric values.
  3. Date/time values based on `java.time` classes.
  4. Fixed values
  5. Values based on probabilities.

### Fixed

### Security

- [TESFOUND-142](https://sunepoulsen.atlassian.net/browse/TESFOUND-142): Test data generators
  - Update dependencies to latest
