#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 <<-EOSQL
  CREATE DATABASE grpc;
  CREATE DATABASE grpc_test;
EOSQL

psql -v ON_ERROR_STOP=1 -d api <<-EOSQL
  CREATE EXTENSION IF NOT EXISTS "postgis";
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
EOSQL

psql -v ON_ERROR_STOP=1 -d api_test <<-EOSQL
  CREATE EXTENSION IF NOT EXISTS "postgis";
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
EOSQL
