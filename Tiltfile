# Build
custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # Command to first build the package with Maven wrapper (skipping tests) and then build the Docker image
    command = './mvnw clean package -DskipTests && docker build --platform=linux/arm64 -t $EXPECTED_REF .',
    # Files to watch that trigger a new build
    deps = ['pom.xml', 'src', 'Dockerfile']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])