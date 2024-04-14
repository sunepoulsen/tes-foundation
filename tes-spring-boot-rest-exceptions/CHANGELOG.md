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

- [TESENTLABS-27](https://sunepoulsen.atlassian.net/browse/TESENTLABS-27): Manage data sets in tel-testdata
  - Producing a `ServiceValidationErrorModel` when handling validation errors.

### Fixed

- [TES-135](https://sunepoulsen.atlassian.net/browse/TES-135): Change artifact groupId
  - GroupId is changed to `dk.sunepoulsen.tes-foundation`
- [VIDADDR-5](https://sunepoulsen.atlassian.net/browse/VIDADDR-5): Create basic skeleton for ViDA.DDR
  - Fixing potential bugs reported by `SonarQube`

### Security

- [TESENTLABS-37](https://sunepoulsen.atlassian.net/browse/TESENTLABS-37): Creation of docker image of tel-web should
  be placed in a Gradle sub module
  - Update dependencies to latest

## 1.0.0 - 2023-11-18

### Features

- [TES-120](https://sunepoulsen.atlassian.net/browse/TES-120): Tech Easy Solutions as a monolith
  - Global exception handler for Spring Boot for TES backends. 

### Security

- [TES-120](https://sunepoulsen.atlassian.net/browse/TES-120): Tech Easy Solutions as a monolith
  - Update dependencies to latest
