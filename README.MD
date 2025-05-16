# Português

# Uso:

### Índice:

[Comandos](#comandos)

[Permissões](#permissões)

[Dependências](#dependências)

[Compatível](#compatível)

[Placeholders](#placeholders)

[Mensagens](#mensagens)

[Configuração do Banco de dados](#configuração-do-database)

## Comandos:

    comando: /reloadconfig [guiconfig | messagens]
        aliases: /rlc
        descrição: Recarrega os arquivos Message.yml e Database.yml, aplicando a configuração com o servidor online. Quando o comando usado for: /reloadconfig database, ele irá recarregar o arquivo e reconectar com o banco de dados.
    comando: /profission (player)
        aliases: /profissao
                 /professionselect
                 /jobselect
                 /profissaoset
        descrição: Abre uma GUI para os jogadores escolherem a profissão. Quando usado o comando /profession [nick], abre uma GUI para o jogador mencionado para que ele escolha a profissão, mesmo que ele já tenha uma profissão definida (OBS: não é possível o jogador ter mais que uma profissão, ou seja, quando esse comando for executado, ele irá sobrescrever a profissão que já existe no jogador). O comando /profession [nick] requer a permissão: jobs.jobselect.others
    comando: /jobs [help | reload | dbbackup | dbrestore] (list)
                   jobs.admin.backup - para executar o comando /jobs dbbackup (list)
        descrição: Quando usado /jobs reload, recarrega o plugin
        quando usardo /jobs dbbackup (nome_do_backup) cria um backup do banco de dados que está sendo usado no momento.
        O nome do backup é opcional. 
        /jobs dbbackup [listar|list] mostra todos os backups na pasta, com data e hora do backup
        /jobs dbrestore [nome_do_backup] irá restaurar o mencionado, é importante da /jobs dbrestore confirm [nome_do_backup]
        e reiniciar o servidor para que a restauração se complete.
        comando: /working
        descrição: Algumas profissões tem habilidade especiais ativas e esse comando ativa elas

## Permissões
| Permissões            | Descrição                                                                 |
|-----------------------|---------------------------------------------------------------------------|
| jobs.reloadconfig     | permissão para executar o comando /reloadconfig                           |
| jobs.jobselect        | permissão para executar p comando /jobselect                              |
| jobs.jobselect.others | permissão para executar o comando /jobselect (player)                     |
| jobs.reload           | permissão para executar o comando /jobs reload                            |
| jobs.backup           | permissão para executar o comando /jobs backup                            |
| jobs.admin.backup     | permissão para executar o comando /jobs backup list <br/> e /jobs restore |
| jobs.working          | permissão para executar o comando /working                                |

## Dependências:

* Banco de dados MySql
* Paper/Bukkit

## Compatível

* [AuraSkills](https://aurelium.dev/auraskills/download)
* [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Placeholders

    - %professions_hasProfission_player_(nick)% - Se o player não tiver nenhuma profissão retorna false, se tem
        retorna true. Configuravél no Message.YML
    - %professions_jobName_player_(nick)% - retorna a profissão do player
    - %professions_hasWorking_player_(nick)% - retorna se o status de "trabalhando" do comando /trabalhar
        se está false ou true. Configuravél no Message.YML


## Mensagens

A maioria das mensagens do plugin está no arquivo 'Message.yml'. Fique à vontade para modificar.

    Messages:
      Prefix: '&6[Professions] '
      NoPermission: '&cVocê não tem permissão para usar este comando!'
      Msg_Command_Error: '&cOcorreu um erro ao executar este comando.'
      Msg_Target_Error: '&c''%s'' não é um jogador.'
      Placeholder:
        hasPermission_false: 'False'
        hasPermission_true: 'True'
        hasWorking_false: Folgando
        hasWorking_true: Trabalhando
      Database:
        InvalidUUID: '&cUUID inválido: '
        UsingMySQL: §aUsando MySQL como banco de dados
        UsingSQLite: §aUsando SQLite como banco de dados
        Unknown_Database: '&cTipo de banco de dados desconhecido. Usando SQLite como padrão.'
        Msg_tableDB_success: '&aTabela %s criada/verificada com sucesso.'
        Msg_tableDB_error: '&cErro ao criar/verificar a tabela %s: '
        Msg_db_success: '&aDatabase %s criada/verificada com sucesso.'
        Msg_db_error: '&cErro ao criar/verificar a database ''%s'': '
        Msg_connectionDB_finished: '&aConexão com o banco encerrada.'
        Msg_connectionDB_finishedError: '&cErro ao encerrar a conexão.'
        Backup_Success: 'Backup do %s criado com sucesso: '
        Backup_Error: '&cBackup do %s não foi criado: '
        Msg_Restore_error: 'Erro ao restaurar o backup: '
        Msg_Restore_Success: 'Backup do %s restaurado com sucesso a partir de: '
        MySQL:
          Msg_connectionDB_success: '&aConexão com o banco de dados estabelecida com sucesso.'
          Msg_connectionDB_failed: '&cFalha ao conectar ao banco.'
          Msg_IncorrectFieldsDB: '&cCampos do MySQL não preenchidos corretamente no Database.YML.'
          Msg_command_DisconnectDB: '&aDesconectando do banco de dados.'
          Msg_command_ConnectDB: '&aReconectando ao banco de dados.'
          Msg_ReconnectingDB: '&eTentando reconectar ao banco de dados'
          Restore_NoFindFile: '&cArquivo de backup não encontrado: '
          Restore_SQLError: '&cErro ao executar comando SQL durante restauração: '
        SQLite:
          Msg_BackupFile_error: '&cNão foi possível criar o diretório de backup: '
          Msg_BackupConnection_error: '&cNão é possível fazer backup: conexão com SQLite está fechada'
          Msg_RestoreFile_error: '&cArquivo de banco de dados SQLite não encontrado:'
      File:
        Msg_Error_Saved: '&cNão foi possível salvar o arquivo messages.yml.'
        Msg_FileCreated: '&2O arquivo messages.yml foi criado com sucesso.'
        Msg_Error_Creation: '&cErro ao criar o arquivo ''messages.yml'': '
        Msg_FileLoaded: '&aArquivo de mensagens ''messages.yml'' carregado.'
        Msg_CreationFileDB_success: '&2Database.yml foi criado com sucesso.'
        Msg_SaveFileDB_Error: '&cErro ao salvar o arquivo Database.yml.'
        Msg_ReloadConfigDB: '&2Database.yml foi recarregado com sucesso.'
        Msg_NoFoundDB_file: '&2Arquivo Database.yml não encontrado.'
      Commands:
        ReloadConfig:
          Msg_Command_ReloadConfig: '&2Arquivo messages.yml recarregado com sucesso.'
          Msg_Command_ReloadConfigDB_Error: '&cFalha ao conectar ao banco de dados. Verifique o Database.yml e reinicie o plugin'
          Msg_Command_ReloadConfigGuiYML: '&2Arquivo ConfigGUI.yml recarregado com sucesso.'
          Msg_Command_ReloadConfigGuiYML_Error: '&cErro ao recarregar o arquivo ConfigGUI.yml. Verifique os logs.'
          Msg_Command_ReloadConfigGuiYML_Create: '&2Arquivo ConfigGUI.yml criado com sucesso.'
          Msg_Command_ReloadConfigGuiYML_Create_Error: '&cErro ao criar o arquivo ConfigGUI.yml. Verifique os logs.'
        Jobs:
          Msg_Command_JobsReload: '&2Plugin reiniciado com sucesso.'
          Backup:
            Msg_Command_BackupCreating: '&eRealizando backup do banco de dados...'
            Msg_Command_BackupSuccess: '&aBackup criado com sucesso: %filename%'
            Msg_Command_Backup_Success: '&aBackup realizado com sucesso!'
            Msg_Command_BackupError: '&cErro ao criar backup. Verifique o console para mais detalhes.'
            Msg_Command_BackupNoFound: '&cNenhum backup encontrado.'
            Msg_Command_BackupUsage: '&cUso correto: /jobs dbbackup <nome_do_backup>'
            Msg_Command_BackupListUsage: '&cUso correto: /jobs dbbackup list'
          Restore:
            Msg_Command_RestoreSuccess: '&aBackup restaurado com sucesso!'
            Msg_Command_RestoreWarning: '&c&lATENÇÃO! &cRestaurar um backup substituirá TODOS os dados atuais!'
            Msg_Command_RestoreConfirm: '&cDigite ''/jobs dbrestore confirmar %filename% para confirmar.'
            Msg_Command_RestoreBackup: '&2 Restaurando backup: '
            Msg_Command_RestoreUsage: '&cUso correto: /dbrestore confirmar <nome_do_backup>'
          JobsSelect:
            Msg_Command_JobsSelect_InvalidGui: §cAs configurações da GUI estão inválidas. Informe um administrador.
            Msg_Command_JobsSelect_SelectProfission: '§aVocê selecionou a profissão: '
            Msg_Command_JobsSelect_InvalidMaterial: §cO material selecionado é inválido. Informe um administrador.
          Working:
            Msg_Command_Working_Execute_error: '&cOcorreu um erro ao executar este comando.
            Tente novamente. Se o erro persistir, entre em contato com um administrador.'
            Msg_Command_Working_NoJob: '&cVocê ainda não escolheu uma profissão. digite /jobselect'
            Msg_Command_Working_EmptyHand: '&cEsvazie sua mão primeiro'
            Msg_Command_Working_Start: '&aTrabalhando'
            Msg_Command_Working_End: '&aVocê não está mais trabalhando.'




  <!-- Lembrar de verificar se está atualizado última atualização 15/05 -->

## ConfigureGUI

Todos os itens da GUI do comando "/selectjobs" estão listados no aquivo ConfigGUI.

    ConfigGUI:
    gui:
        title: Seleção de Profissões
        items:
            archer:
                slot: 12
                material: BOW
                display_name: &aArqueiro
                lore:
                - Uma profissão focada em ataques de longa distância.
                - Especialista em combate com arco.
            enchanter:
                slot: 13
                material: ENCHANTING_TABLE
                display_name: &bEncantador
                lore:
                - Você é o mestre da magia.
                - Encante itens como nenhum outro.
            lumberjack:
                slot: 14
                material: IRON_AXE
                display_name: &6Lenhador
                lore:
                - Profissão ideal para cortar árvores
                - Ajude a coletar madeira para sobrevivência.
            farmer:
                slot: 20
                material: WHEAT
                display_name: &2Fazendeiro
                lore:
                - Agricultura para todos.
                - Cultive e colha para sustentar seu time.
            fisherman:
                slot: 21
                material: FISHING_ROD
                display_name: &3Pescador
                lore:
                - A pesca é uma arte.
                - Conquiste os mares.
            miner:
                slot: 22
                material: DIAMOND_PICKAXE
                display_name: &9Mineiro
                lore:
                - Subterrâneo, o reino dos mineiros.
                - Encontre minerais valiosos.
            tank:
                slot: 23
                material: DIAMOND_CHESTPLATE
                display_name: &eTanque
                lore:
                - Sustente o dano pelo grupo.
                - Perfeito para defesa.
            agility:
                slot: 24
                material: FEATHER
                display_name: &fAgilidade
                lore:
                - Movimento rápido é sua vantagem.
                - Evite ataques com rapidez.
            warrior:
                slot: 30
                material: IRON_SWORD
                display_name: &cGuerreiro
                lore:
                - Lute de frente.
                - Profissão para combatentes.
            alchemist:
                slot: 31
                material: BREWING_STAND
                display_name: &5Alquimista
                lore:
                - Misture líquidos misteriosos.
                - Proporcione novas possibilidades ao time.
            excavator:
                slot: 32
                material: GOLDEN_SHOVEL
                display_name: &6Escavador
                lore:
                - Remova areia e cascalho rápido.
                - Descubra tesouros enterrados.
### Avisos
O slot do item não pode ser maior que 45(último slot) se estiver incorreta o comando irá da erro.
O material tem que ser válido: [Materiais válidos](https://dev.bukkit.org/projects/supplies/pages/material-list)

## Configuração do Database

A conexão com o banco de dados é feita por meio do arquivo database.yml

    Database.yml
        #SQLite or MySQL
        Database:
            Type: SQLite
        MySql:
            HOST: localhost
            PORT: 3306
            USER: root
            PASSWORD: root
            DATABASE: professions
            TABLE: jobs

* ### SQLite:
  Basta alterar o "Database: Type:" para "SQLite"

* ### MySQL:
  Para se conectar ao Database você irá precisar do endereço, porta, usuário, senha, database e tabela.

  Os campos opcionais é o de "DATABASE" e o "TABLE". Se ainda não existir um banco de dados e ou uma tabela com esse nome ele irá criar e se conectar. Recomendo que modifique esse campo apenas se houver conflito com algum banco de dados já existente.

  Você precisará colocar o ip SEM A PORTA no campo "HOST" e a SÓ a porta no campo "PORT".

  O "USER" e "PASSWORD" pode ser colocado a padrão porém se você pode criar um usuário basta executar esses comandos no terminal MySQL:

        CREATE user 'profession'@'ip_do_database' IDENTIFIED BY 'senha';
        GRANT ALL PRIVILEGES ON nome_do_database TO 'profession'@'ip_do_database';
        FLUSH PRIVILEGES;

1. Na primeira linha temos o 'profession'@'ip_do_database'. O profession é o nome de usuário a ser criado e você terá que colocar esse usuário no campo USER no Database.yml. No ip_do_database será colocado @'%' para que ele se conecte de qualquer lugar ou se ou se preferir pode também colocar o ip da maquina qual deseja se conectar. A 'senha' tem que ser igual a "PASSWORD"

2. Na segunda linha você define os acessos do usuário é importante que seja mantido o ALL PRIVILEGES isso define que o usuário terá acesso total database definido em nome_do_database. O nome_do_database é database que o plugin vai acessar para salvar, ler e apagar os dados. É importante que esse nome seja igual ao "DATABASE" do Database.yml. Outra observação é que esse usuário não terá a permissão de criar o database então é importante que você crie esse database manualmente através do comando

       CREATE DATABASE nome_do_database;
   Esse nome_do_database deve ser igual ao "DATABASE".

3. A função da última linha é somente atualizar e aplicar as mudanças


    