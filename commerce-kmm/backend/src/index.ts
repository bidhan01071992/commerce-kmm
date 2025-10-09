import { ApolloServer } from "@apollo/server";
import { startStandaloneServer } from "@apollo/server/standalone";
import { config } from "dotenv";
import typeDefs from "./Schema/ProductSchema.js";
import productResolver from "./Resolver/ProductResolver.js";


async function start() {
  config();
  const envPort = process.env.PORT || 4000;
  const server = new ApolloServer({
    typeDefs,
    resolvers: productResolver
  });
  const { url } = await startStandaloneServer(server, {
    listen: { port: Number(envPort) },
});

console.log(`🚀  Server ready at: ${url}`);
}


start().catch(e => {
  console.error(e);
  process.exit(1);
});

