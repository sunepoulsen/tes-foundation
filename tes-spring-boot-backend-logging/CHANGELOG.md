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

- [TES-120](https://sunepoulsen.atlassian.net/browse/TES-120): Tech Easy Solutions as a monolith

For release-dates, use date-format: YYYY-MM-DD

## Unreleased work

### Features

### Fixed

- [TES-135](https://sunepoulsen.atlassian.net/browse/TES-135): Change artifact groupId
  - GroupId is changed to `dk.sunepoulsen.tes-foundation`

### Security

- [TESENTLABS-37](https://sunepoulsen.atlassian.net/browse/TESENTLABS-37): Creation of docker image of tel-web should
  be placed in a Gradle sub module
  - Update dependencies to latest

## 1.0.0 - 2023-11-18

### Features

- [TES-120](https://sunepoulsen.atlassian.net/browse/TES-120): Tech Easy Solutions as a monolith
  - Common logging capabilities for Spring Boot backends. 

### Security

- [TES-120](https://sunepoulsen.atlassian.net/browse/TES-120): Tech Easy Solutions as a monolith
  - Update dependencies to latest versions.
