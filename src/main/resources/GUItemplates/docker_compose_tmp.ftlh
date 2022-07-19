version: '3.3'
services:
  ol-general-container:
    image: openlegacy/mf-cics-ts:1.1.48
    container_name: ol-general-container
    ports:
      - "8080:${port}"
    volumes:
      - ./noCode.json:/app/config/config.json