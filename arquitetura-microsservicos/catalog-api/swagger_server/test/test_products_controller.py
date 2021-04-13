# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from swagger_server.models.product_item import ProductItem  # noqa: E501
from swagger_server.test import BaseTestCase


class TestProductsController(BaseTestCase):
    """ProductsController integration test stubs"""

    def test_add_product(self):
        """Test case for add_product

        Adiciona um produto novo na lista
        """
        produto = ProductItem()
        response = self.client.open(
            '/bylucasxdx/CatalogApi/1.0.0/products',
            method='POST',
            data=json.dumps(produto),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_search_products(self):
        """Test case for search_products

        Busca de produtos
        """
        query_string = [('nameProduct', 'nameProduct_example'),
                        ('limit', 50)]
        response = self.client.open(
            '/bylucasxdx/CatalogApi/1.0.0/products',
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
