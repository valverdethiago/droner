FROM ubuntu:16.04

RUN apt-get update && apt-get install -y \
  build-essential \
  git \
  openjdk-8-jdk \
  maven \
  curl

RUN curl -sL https://deb.nodesource.com/setup_8.x | bash - \
  && apt-get install -y nodejs

RUN git clone https://github.com/valverdethiago/droner.git /app 

ADD startup.sh /app

RUN chmod 777 /app/startup.sh

RUN mvn clean install -f /app/backend/pom.xml 

RUN npm --prefix /app/frontend install /app/frontend 

EXPOSE 4200

EXPOSE 8080

#ENTRYPOINT app/startup.sh 


