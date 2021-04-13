import connexion
import six

from swagger_server.models.campanha_item import CampanhaItem  # noqa: E501
from swagger_server import util


def add_campanha(produto=None):  # noqa: E501
    """Adiciona uma nova campanha na lista

    Adiciona uma nova campanha na lista de marketing # noqa: E501

    :param produto: Campanha que será adicionada
    :type produto: dict | bytes

    :rtype: None
    """
    if connexion.request.is_json:
        produto = CampanhaItem.from_dict(connexion.request.get_json())  # noqa: E501
    return ({
        "tipo": "sucesso",
        "message": "Campanha adicionada com sucesso!"
    }, 201)

def search_campanhas(nomeCampanha=None, limit=None):  # noqa: E501
    """Busca das campanhas de marketing

    Enviando informações é possível encontrar campanhas de marketing  # noqa: E501

    :param nomeCampanha: Enviando uma string opcional é possível filtrar as campanhas de marketing atuais
    :type nomeCampanha: str
    :param limit: Máximo de itens que podem ser retornados
    :type limit: int

    :rtype: List[CampanhaItem]
    """
    return ({
        "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
        "name": "Campanha de exemplo",
        "public": "Pessoas com mais de 18 anos"
    }, 200)
