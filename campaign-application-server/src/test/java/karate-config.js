function fn() {
  const env = karate.properties['karate.env'] || 'local';
  const port = karate.properties['karate.port'] || '8080';

  const config = {
    env: env,
    baseUrl: 'http://localhost:' + port
  }
  return config;
}
