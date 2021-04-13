<?php
require_once __DIR__ . '/vendor/autoload.php';

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Silex\Application;

$app = new Silex\Application();


$app->POST('/bylucasxdx/LocationsApi/1.0.0/localizacoes', function(Application $app, Request $request) {
	return new Response(json_encode('Localização salva com sucesso!'), 201);
});


$app->GET('/bylucasxdx/LocationsApi/1.0.0/localizacoes', function(Application $app, Request $request) {
	$id_usuario = $request->get('id_usuario');
	$limit = $request->get('limit');
	return new Response(json_encode(array("id" => "d290f1ee-6c54-4b01-90e6-d701748f0851",
		"idUsuario" => 1,
		"latitude" => "-12.1023091203",
		"longitude" => "-40.1203910203"
	)));
});


$app->run();
