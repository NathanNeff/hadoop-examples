# Python REST API for Cloudera Manager

These are my dabblings for using the Python interface to 
work with the Cloudera REST API

# Installation

Basically, I ran =sudo pip install cm_api=

        - http://cloudera.github.io/cm_api/

        - Great examples of installation and usage
                - http://cloudera.github.io/cm_api/docs/python-client/


        - When you deal with the results of these Python calls, the "objects" fit this model:
                - http://cloudera.github.io/cm_api/epydoc/5.0.0/index.html
                - http://cloudera.github.io/cm_api/apidocs/v6/model.html

        - The terms used by Cloudera Manager, like "Roles", "Role Groups" etc. are detailed here,
          and it pays off big-time to understand the hierarchy and relationships between these entities

          http://www.cloudera.com/content/cloudera-content/cloudera-docs/CM5/latest/Cloudera-Manager-Introduction/cm5i_primer.html?scroll=concept_wfj_tny_jk_unique_1

# Examples

## Simple List Cluster Properties
    - Prints names of the clusters that are managed by CM
    - Dives a bit into the properties of clusters, like hosts, roles, etc.
    - [Simple Cluster Properties](simple_cluster_properties.py)

## Dump Cluster Configurations
  - [CM Dump Config](cm-dump-config.py)

## Show Configuration for Role Instance
  - Shows configuration settings for a specific DataNode in a cluster
  - [Simple Configuration Settings Example](simple_config_settings.py)


