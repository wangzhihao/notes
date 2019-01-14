# Presentation
Here is [a presentation slides](https://docs.google.com/presentation/d/1D-XhCmrgKec7Ug5kYABh7Dt9JLgyTa2_i3rqvfLU-C0/edit?usp=sharing) on api design.
# GraphQL Aha moments.
1. It's mandatory for clients to declare the fields they wants in GraphQL. This can be helpful to [track field usage and thus support deprecation more easily](https://philsturgeon.uk/api/2017/01/24/graphql-vs-rest-overview/), and to save traffic.
2. client side issued query script/DSL?  To what extend of flexibility are supported? Currently I only see id-based filter. For example, how to support filter?
3. How does it influence server-side query and storage? Currently I see a very different server requirements, i.e. the resolvers for each field. Is there any limitation? 
4. Some analogs, DSL like datapath, data turboflow, responseGroup of our implementations, RPC like coral, the idea of adaptive and forecast the data fields changes. [[1]](https://sites.google.com/a/athaydes.com/renato-athaydes/posts/thereturnofrpc-orhowrestisnolongertheonlyrespectablesolutionforapis#TOC-GraphQL) 

# Relay Aha moments
1. Relay is a glue to bind React and GraphQL, and thus the name Relay.
2. [It maintains a cache underlying](https://facebook.github.io/relay/docs/en/runtime-architecture.html). With cache, it's possible to update related data automatically. A similar purpose tool is [Apollo GraphQL](https://github.com/apollographql/apollo-client)

# Dataloader Aha moments
1. Address [n + 1 problem](https://medium.com/slite/avoiding-n-1-requests-in-graphql-including-within-subscriptions-f9d7867a257d)

# REST Aha moments
1. HATEOAS [[Roy Fielding's comment]](https://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven) / [[Wikipedia]](https://en.wikipedia.org/wiki/HATEOAS) / [[github api]](https://api.github.com/)

# GraphiQL queries examples
__Test endpoint__: https://github.com/graphql/graphiql

Introspection
```
{
  __schema {
    types {
      name
    }
    queryType {
      name
    }
    mutationType {
      name
    }
    subscriptionType {
      name
    }
    directives {
      name
    }
  }
}
```

[Pagination](https://graphql.org/learn/pagination/)
```
{
  allFilms(first: 2, after: "YXJyYXljb25uZWN0aW9uOjE=") {
    pageInfo {
      startCursor
      endCursor
    }
    totalCount
    edges {
      node {
        id
        title
      }
      cursor
    }
  }
}
```

Search and filter
```
{
  film(id: "ZmlsbXM6Mw==") {
    id
    title
  }
}
```

# Reference
* Some resources: https://github.com/chentsulin/awesome-graphql
* AWS AppSync: https://aws.amazon.com/appsync/
* Relay: https://code.fb.com/web/relay-modern-simpler-faster-more-extensible/
* Relay Runtime: https://facebook.github.io/relay/docs/en/runtime-architecture.html
* Apollo GraphQL: https://www.apollographql.com/
* Why Apollo GraphQL: https://www.apollographql.com/docs/react/why-apollo.html
* Generate Executable Schema: https://www.apollographql.com/docs/graphql-tools/generate-schema.html
* GraphiQL, the in-browser query tool: https://github.com/graphql/graphiql
* Some comparison betwween REST/RPC/GraphQL: https://blog.apisyouwonthate.com/understanding-rpc-rest-and-graphql-2f959aadebe7
* Sample Code in java for a whole graphql application: https://sites.google.com/a/athaydes.com/renato-athaydes/posts/thereturnofrpc-orhowrestisnolongertheonlyrespectablesolutionforapis#TOC-GraphQL
