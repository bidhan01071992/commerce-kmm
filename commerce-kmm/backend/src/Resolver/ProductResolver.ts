import productsData from "../Model/Product.js";

const productResolver = {
    Query: {
        products: () => productsData,
    },
};

export default productResolver;