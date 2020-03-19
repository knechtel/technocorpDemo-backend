# technocorpDemo-backend


documentacao
http://localhost:8080/swagger-ui.html#/
Toda a implementação esta em linhaController.

Existe uma unica funcionalidade no IntinerarioController que é para buscar da API todos o intinerario de cada linha.

    /*
    * chamar primeiro /api/linha/lista
    * este metodo busca todos itinerários na Web
    * */
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity gettAllIntinerario(){
        intinerarioService.getAll();
        return new ResponseEntity(HttpStatus.OK);
    }
