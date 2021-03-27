PROPS = src/main/resources/application-resources.properties
run:	$(PROPS)
	mvn quarkus:dev
$(PROPS):
	cd src/main/resources; [ -f application.properties ] || cp application.properties.sample application.properties
docker-image:
	./mvnw -DskipTests package -Dquarkus.container-image.build=true
