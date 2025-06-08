import swaggerJsdoc from 'swagger-jsdoc';

const options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Journey API',
      version: '1.0.0'
    },
    paths: {
      '/api/random-act': {
        get: {
          summary: 'Get a random act from Tome of Adventure Design',
          responses: {
            200: {
              description: 'Random act',
              content: {
                'application/json': {
                  schema: {
                    type: 'object',
                    properties: {
                      result: { type: 'string' }
                    }
                  }
                }
              }
            }
          }
        }
      },
      '/api/time-cycle': {
        get: {
          summary: 'Get a random time cycle from Tome of Adventure Design',
          responses: {
            200: {
              description: 'Random time cycle',
              content: {
                'application/json': {
                  schema: {
                    type: 'object',
                    properties: {
                      result: { type: 'string' }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  },
  apis: []
};

const swaggerSpec = swaggerJsdoc(options);
export default swaggerSpec;
