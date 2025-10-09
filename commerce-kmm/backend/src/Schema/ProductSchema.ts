
// A schema is a collection of type definitions (hence "typeDefs")
// that together define the "shape" of queries that are executed against
// your data.
const typeDefs = `#graphql
  # Comments in GraphQL strings (such as this one) start with the hash (#) symbol.
  # This "Product" type defines the queryable fields for every product in our data source.
  type Product {
    productId: ID!
    productName: String!
    productImageURL: String
    quantity: Int!
    price: Float!
  }
  # The "Query" type is special: it lists all of the available queries that
  # clients can execute, along with the return type for each. In this
  # case, the "products" query returns an array of zero or more Producsts (defined above).
  type Query {
    products: [Product!]!
  }
`;

export default typeDefs;