package dao

import models.Films

class FilmDAO {

    //Funcionando  -  Precisa Melhorar !!
    //Função para pegaar um filme com base no seu id.
    //Function to get one film from table by id.
    fun getOne(id : Int): Films? {
        val connectionDAO = ConnectionDAO()
        var film : Films? = null        //isso aqui está ruim
        try {
            val resultSet = connectionDAO.executeQuery("SELECT * FROM Films WHERE id = ${id};")
            while(resultSet?.next()!!){
                film = Films(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("genre"),
                        resultSet.getString("director"),
                        resultSet.getString("date"))
            }
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally {
            connectionDAO.close()
        }
        return film!!
    }

    //Funcionando  -  Precisa Melhorar !!
    //Função para pegar um filme da tabela pelo nome.
    //Function to get one film from table by the name.
    fun getOne(name : String): Films? {
        val connectionDAO = ConnectionDAO()
        var film : Films? = null        // Isso aqui está ruim
        try {
            val resultSet = connectionDAO.executeQuery("SELECT * FROM Films WHERE name = '${name}';")
            while(resultSet?.next()!!){
                film = Films(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("genre"),
                    resultSet.getString("director"),
                    resultSet.getString("date"))
            }
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally {
            connectionDAO.close()
        }
        return film!!
    }

    //Funcionando
    //Função para pegar toda a tabela 'Films'.
    //Function to get all elements from Films table.
    fun getAll(): List<Any> {
        val connectionDAO = ConnectionDAO()
        val films = mutableListOf<Films>()
        try{
            val resultSet = connectionDAO.executeQuery("SELECT * FROM films;")
            while(resultSet?.next()!!){
                films.add(
                    Films(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("genre"),
                        resultSet.getString("director"),
                        resultSet.getString("date"),
                    )
                )
            }
        }
        catch(exception : Exception){
            exception.stackTrace
        }
        finally{
            connectionDAO.close()
        }
        return films
    }

    //Funcionando  -  Melhorar !
    //Função que permite adicionar um filme na tabela.
    //Function to add film in table.
    fun addOne(obj : Any){
        val connectionDAO = ConnectionDAO()
        try {
            val preparedStatement = connectionDAO.getPreparedStatement(
                """
            INSERT INTO Films
            (name, genre, director, date)
            VALUES (?, ?, ?, ?)
        """.trimIndent()
            )
            val film = obj as Films
            preparedStatement?.setString(1, film.name)
            preparedStatement?.setString(2, film.genre)
            preparedStatement?.setString(3, film.director)
            preparedStatement?.setString(4, film.date)
            preparedStatement?.executeUpdate()
            //connectionDAO.commit()
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally{
            connectionDAO.close()
        }
    }

    //Funcionando
    //Função que permite adicinar todos os filmes em uma lista.
    //Function to add all films in list to table.
    fun addAll(list : List<Any>){
        val connectionDAO = ConnectionDAO()
        try{
            val preparedStatement = connectionDAO.getPreparedStatement("""
                INSERT INTO Films
                (name, genre, director, date)
                VALUES (?, ?, ?, ?)
            """.trimIndent())
            for (obj in list){
                val film = obj as Films
                preparedStatement?.setString(1,obj.name)
                preparedStatement?.setString(2,obj.genre)
                preparedStatement?.setString(3,obj.director)
                preparedStatement?.setString(4,obj.date)
                preparedStatement?.executeUpdate()
            }
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally{
            connectionDAO.close()
        }
    }

    //Funcionando
    //Função permite deletar um filme da tabela pelo seu id.
    //Function to delete one film from table by the id.
    fun delete(id : Int){
        val connectionDAO = ConnectionDAO()
        try {
            val preparedStatement = connectionDAO.getPreparedStatement("""
                DELETE FROM Films
                WHERE id = ?;
                """.trimIndent()
            )
            preparedStatement?.setInt(1, id)
            preparedStatement?.executeUpdate()
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally{
            connectionDAO.close()
        }
    }

    //Funcionando
    //Função permite atualizar um filme da tabela.
    //Function to update one film from table.
    fun update(obj : Any){
        val connectionDAO = ConnectionDAO()
        try {
            val preparedStatement = connectionDAO.getPreparedStatement(
                """
            UPDATE Films
            SET name = ?, genre = ?, director = ?, date = ?
            WHERE id = ?;
        """.trimIndent()
            )
            val film = obj as Films
            preparedStatement?.setString(1, film.name)
            preparedStatement?.setString(2, film.genre)
            preparedStatement?.setString(3, film.director)
            preparedStatement?.setString(4, film.date)
            preparedStatement?.setInt(5, film.id)
            preparedStatement?.executeUpdate()
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally{
            connectionDAO.close()
        }
    }

    //Funcionando
    //Função permite calcular nota média de um filme com base nos avaliações(reviews) dos usuários.
    //Function to get the film score/grade based in users reviews.
    fun score(id : Int) : Double{
        val connectionDAO = ConnectionDAO()
        var film : Films? = null        //isso aqui está ruim
        var score : Double = 0.0
        var n : Int = 0
        try {
            val resultSet = connectionDAO.executeQuery("SELECT score FROM Reviews WHERE idFilm = ${id};")
            while(resultSet?.next()!!){
                score += resultSet.getDouble("score")
                n++
            }
        }
        catch(exception:Exception){
            exception.stackTrace
        }
        finally {
            connectionDAO.close()
        }
        return score/n
    }
}