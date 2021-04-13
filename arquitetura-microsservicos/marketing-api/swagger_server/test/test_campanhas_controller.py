# coding: utf-8

from __future__ import absolute_import

from flask import json
from six import BytesIO

from swagger_server.models.campanha_item import CampanhaItem  # noqa: E501
from swagger_server.test import BaseTestCase


class TestCampanhasController(BaseTestCase):
    """CampanhasController integration test stubs"""

    def test_add_campanha(self):
        """Test case for add_campanha

        Adiciona uma nova campanha na lista
        """
        produto = CampanhaItem()
        response = self.client.open(
            '/bylucasxdx/MarketingApi/1.0.0/campanhas',
            method='POST',
            data=json.dumps(produto),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_search_campanhas(self):
        """Test case for search_campanhas

        Busca das campanhas de marketing
        """
        query_string = [('nomeCampanha', 'nomeCampanha_example'),
                        ('limit', 50)]
        response = self.client.open(
            '/bylucasxdx/MarketingApi/1.0.0/campanhas',
            method='GET',
            query_string=query_string)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    import unittest
    unittest.main()
