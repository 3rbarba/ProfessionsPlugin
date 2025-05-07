# Português

# Uso:

### Índice:

[Comandos](#comandos)

[Dependências](#dependências)

[Compatível](#compatível)

[Placeholders](#placeholders)

[Mensagens](#mensagens)

[Configuração do Banco de dados](#configuração-do-database)

## Comandos:

    comando: /reloadconfig [database|message]
        aliases: /rlc
        permissão: jobs.reloadconfig
        descrição: Recarrega os arquivos Message.yml e Database.yml, aplicando a configuração com o servidor online. Quando o comando usado for: /reloadconfig database, ele irá recarregar o arquivo e reconectar com o banco de dados.
    comando: /profission
        aliases: /profissao
                 /professionselect
                 /jobselect
                 /profissaoset
        permissão: jobs.jobselect
                   jobs.jobselect.others
        descrição: Abre uma GUI para os jogadores escolherem a profissão. Quando usado o comando /profession [nick], abre uma GUI para o jogador mencionado para que ele escolha a profissão, mesmo que ele já tenha uma profissão definida (OBS: não é possível o jogador ter mais que uma profissão, ou seja, quando esse comando for executado, ele irá sobrescrever a profissão que já existe no jogador). O comando /profession [nick] requer a permissão: jobs.jobselect.others
    comando: /jobs
        permissão: jobs
        descrição: Quando usado /jobs reload, recarrega o plugin

## Dependências:

* Banco de dados MySql
* Paper/Bukkit

## Compatível

* [AuraSkills](https://aurelium.dev/auraskills/download)
* [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Placeholders

    - %hasProfission_player_(Nick)% - Se o player não tiver nenhuma profissão retorna false, se tem
        retorna true. Configuravél no Message.YML
    - %professions_jobName_player_(Nick)% - retorna a profissão do player


## Mensagens

A maioria das mensagens do plugin está no arquivo 'Message.yml'. Fique à vontade para modificar.

    Message.yml:
        Messages:
        Prefix: '&6[Professions] '
        NoPermission: '&cVocê não tem permissão para usar este comando!'
        Msg_Command_Error: '&cOcorreu um erro ao executar este comando.'
        Msg_Target_Error: '&c''%s'' não é um jogador.'
            Placeholder:
                hasPermission_false: 'False'
                hasPermission_true: 'True'
            Database:
                Msg_NoFoundDB_file: '&2Arquivo SqlConection não encontrado.'
                Msg_connectionDB_success: '&aConexão com o banco de dados estabelecida com sucesso.'
                Msg_connectionDB_failed: '&cFalha ao conectar ao banco.'
                Msg_connectionDB_finished: '&aConexão com o banco encerrada.'
                Msg_connectionDB_finishedError: '&cErro ao encerrar a conexão.'
                Msg_IncorrectFieldsDB: '&cCampos do MySQL não preenchidos corretamente no Database.YML.'
                Msg_command_DisconnectDB: '&aDesconectando do banco de dados.'
                Msg_command_ConnectDB: '&aReconectando ao banco de dados.'
                Msg_db_success: '&aDatabase %s criada/verificada com sucesso.'
                Msg_db_error: '&cErro ao criar/verificar a database ''%s'': '
                Msg_tableDB_success: '&aTabela %s criada/verificada com sucesso.'
                Msg_tableDB_error: '&cErro ao criar/verificar a tabela %s: '
                Msg_CreationFileDB_success: '&2Database.yml foi criado com sucesso.'
                Msg_SaveFileDB_Error: '&cErro ao salvar o arquivo Database.yml.'
                Msg_ReloadConfigDB: '&2Database.yml foi recarregado com sucesso.'
            File:
                Msg_Error_Saved: '&cNão foi possível salvar o arquivo messages.yml.'
                Msg_FileCreated: '&2O arquivo messages.yml foi criado com sucesso.'
                Msg_Error_Creation: '&cErro ao criar o arquivo ''messages.yml'': '
                Msg_FileLoaded: '&aArquivo de mensagens ''messages.yml'' carregado.'
            Commands:
                ReloadConfig:
                    Msg_SintaxReloadConf_Error: '&cO uso correto é /reloadconfig [database | message
                    | gui].'
                    Msg_Command_ReloadConfig: '&2Arquivo messages.yml recarregado com sucesso.'
                    Msg_Command_ReloadConfigDB_Error: '&cFalha ao conectar ao banco de dados. Verifique
                    o Database.yml e reinicie o plugin'
                    Msg_Command_ReloadConfigGuiYML: '&2Arquivo ConfigGUI.yml recarregado com sucesso.'
                    Msg_Command_ReloadConfigGuiYML_Error: '&cErro ao recarregar o arquivo ConfigGUI.yml.
                    Verifique os logs.'
                    Msg_Command_ReloadConfigGuiYML_Create: '&2Arquivo ConfigGUI.yml criado com sucesso.'
                    Msg_Command_ReloadConfigGuiYML_Create_Error: '&cErro ao criar o arquivo ConfigGUI.yml.
                    Verifique os logs.'
                Jobs:
                    Msg_Command_JobsReloadUsage: '&cUso correto: /jobs reload'
                    Msg_Command_JobsReload: '&2Plugin reiniciado com sucesso.'
                JobsSelect:
                    Msg_Command_JobsSelect_InvalidGui: §cAs configurações da GUI estão inválidas.
                    Informe um administrador.
                    Msg_Command_JobsSelect_SelectProfission: '§aVocê selecionou a profissão: '
                    Msg_Command_JobsSelect_InvalidMaterial: §cO material selecionado é inválido.
                    Informe um administrador.



  <!-- Lembrar de verificar se está atualizado última atualização 05/05 -->

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

* ### MySQL:
  Para se conectar ao Database você irá precisar do endereço, porta, usuário, senha e database.

  O único campo opcional é o de "DATABASE". Se ainda não existir um banco de dados com esse nome ele irá criar e se conectar. Recomendo que modifique esse campo apenas se houver conflito com algum banco de dados já existente.

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