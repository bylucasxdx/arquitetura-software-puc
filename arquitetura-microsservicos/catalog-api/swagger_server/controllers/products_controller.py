import connexion
import six

from swagger_server.models.product_item import ProductItem  # noqa: E501
from swagger_server import util


def add_product(produto=None):  # noqa: E501
    """Adiciona um produto novo na lista

    Adiciona um novo produto ao sistema # noqa: E501

    :param produto: Produto que será adicionado
    :type produto: dict | bytes

    :rtype: None
    """
    if connexion.request.is_json:
        produto = ProductItem.from_dict(connexion.request.get_json())  # noqa: E501
    return ({
        "tipo": "sucesso",
        "message": "Produto adicionado com sucesso!"
    }, 201)


def search_products(nameProduct=None, limit=None):  # noqa: E501
    """Busca de produtos

    Enviando informações é possível encontrar produtos no sistema  # noqa: E501

    :param nameProduct: Enviando uma string opcional é possível filtrar os produtos
    :type nameProduct: str
    :param limit: Máximo de itens que podem ser retornados
    :type limit: int

    :rtype: List[ProductItem]
    """
    return ({
        "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
        "name": "Camisa G - Projeto"
    }, 200)
