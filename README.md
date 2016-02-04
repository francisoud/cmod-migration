# cmod-migration
database migration for IBM content manager ondemand

## Introduction

### Database migration principle
https://en.wikipedia.org/wiki/Data_migration

### IBM CMOD context
IBM CMOD a just a wrapper a 'classical' database adding some functionnalities on top such as data sharding and retention policy.
Juste like classical dataase, maintaining the schema is error prone.
Database migration can solve this and help team work in parallele and keep track of changes in a SCM.
CMOD provide a DML form updating, creating, deleting configuration object (arsxml command line).
This projet just aim to provide a mecanism to track, run and follow what and when update were run for each machine or environnements.

## Getting started

### Installation

### Command line

## Under the hood

### ODCHANGELOG Application Group
