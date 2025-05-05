# Português

# Uso:
### Índice:


[Comandos](#comandos)

[Dependências](#dependências)

[Mensagens](#mensagens)

[Configuração do Banco de dados](#configuração-do-database)


## Comandos:

    comando: /reloadconfig [database|message]
        aliases: /rlc
        permissão: jobs.reloadconfig
        descrição: Recarrega os arquivos Message.yml e Database.yml aplicando a configuração com 
                   o servidor online. Quando o comando usado for: /reloadconfig database 
                   ele irá recarregar o arquivo e reconectar com o banco de dados.
    comando: /profission
        aliases: /profissao
                 /professionselect
                 /jobselect
                 /profissaoset
        permissão: jobs.jobselect
                   jobs.jobselect.others
        descrição: Abre uma GUI para os jogadores escolherem a profissão. Quando usado o comando 
                   /profession [nick] faz com abra uma GUI para o jogador mencionado para que ele
                   escolha a profissão, mesmo que ele já tenha uma profissão definida (OBS: não é
                   possível o jogador ter mais que uma profissão, ou seja, quando esse comando for
                   executado ele irá sobrescrever a profissão que já existe no jogador)
                   o comando /profession [nick] requer a permissão: jobs.jobselect.others
    comando: /jobs
        permissão: jobs
        descrição: Quando usado /jobs reload recarrega o plugin

## Dependências:

* [AuraSkills](https://aurelium.dev/auraskills/download) <!-- pode ser que não seja uma dependencia, apenas uma integração -->
* Banco de dados MySql
* Paper/Bukkit

## Mensagens

A maioria das mensagens do plugin está no arquivo 'Message.yml'. Fique a vontade
para modificar.

    Message.yml:
        Messages:
        NoPermission: '&cVocê não tem permissão para usar este comando!'
        Msg_NoFoundDB_file: '&2Arquivo SqlConection não encontrado'
        Msg_connectionDB_sucess: '&aConexão com o banco de dados estabelecida com sucesso.'
        Msg_connectionDB_failed: '&cFalha ao conectar ao banco.'
        Msg_connectionDB_finished: '&aConexão com o banco encerrada.'
        Msg_connectionDB_finishedError: '&cErro ao encerrar a conexão.'
        Msg_IncorretFieldsDB: '&cCampos do MySQL não preenchidos corretamente no YML.'
        Msg_db_sucess: '&aDatabase %s criada/verificada com sucesso.'
        Msg_db_error: '&cErro ao criar/verificar a database ''%s'': '
        Msg_tableDB_sucess: '&aTabela jobs_data criada/verificada com sucesso.'
        Msg_tableDB_error: '&cErro ao criar/verificar a tabela jobs_data: '
        Msg_CreationFileDB_sucess: ' &2Database.yml foi criado com sucesso.'
        Msg_SaveFileDB_Error: '&cErro ao salvar o arquivo Database.yml'
        Msg_ReloadConfigDB: '&2Database.yml foi recarregado com sucesso.'
        Msg_SintaxReloadConf_Error: '&cO uso correto é /reloadconfig [database | message]'
        Msg_message_ReloadConfig: '&2Arquivo messages.yml recarregado com sucesso'
        Msg_Command_Error: '&c Ocorreu um erro ao executar este comando'
        Msg_command_DisconnectDB: '&aDesconectando do banco de dados.'
        Msg_command_ConnectDB: '&aReconectando ao banco de dados.'
        Msg_Error_Saved: '&cNão foi possivel salvar o arquivo messages.yml'
        Msg_FileCreated: '&2O arquivo messages.yml foi criado com sucesso'
        Msg_Target_Error: '''%s'' não é um jogador'
        Msg_Command_profission_Error: Erro ao executar este comando

  <!-- Lembrar de verificar se está atualizado última atualização 03/05 -->

## Configuração do Database

*   ### MySQL:
    Para se conectar ao Database você irá precisar do endereço, porta, usuário, senha e database.

    O único campo opcional é o de "DATABASE". Se ainda não existir um banco de dados com esse nome
    ele irá criar e se conectar. Recomendo que modifique esse campo apenas se houver conflito com
    algum banco de dados já existente.

    Você precisará colocar o ip SEM A PORTA no campo "HOST" e a SÓ a porta no campo "PORT".

    O "USER" e "PASSWORD" pode ser colocado a padrão porém se você pode criar um usuário
    basta executar esses comandos no terminal MySQL:

        CREATE user 'profession'@'ip_do_database' IDENTIFIED BY 'senha';
        GRANT ALL PRIVILEGES ON nome_do_database TO 'ip_do_database';
        FLUSH PRIVILEGES;

1.  Na primeira linha temos o 'profession'@'ip_do_database'. O profession é o nome de usuário a
    ser criado e você terá que colocar esse usuário no campo USER no Database.yml.
    No ip_do_database será colocado @'%' para que ele se conecte de qualquer lugar ou se 
    ou se preferir pode também colocar o ip da maquina qual deseja se conectar.
    A 'senha' tem que ser igual a "PASSWORD"

2.  Na segunda linha você define os acessos do usuário é importante que seja mantido o ALL PRIVILEGES
    isso define que o usuário terá acesso total database definido em nome_do_database.
    O nome_do_database é database que o plugin vai acessar para salvar, ler e apagar os dados.
    É importante que esse nome seja igual ao "DATABASE" do Database.yml. Outra observação é que esse
    usuário não terá a permissão de criar o database então é importante que você crie esse database
    manualmente através do comando

        CREATE DATABASE nome_do_database;
    Esse nome_do_database ser igual ao "DATABASE"


3. A função da última linha é somente atualizar e aplicar as mudanças
