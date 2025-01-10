# HomeMod

Um mod simples de homes para Minecraft Forge 1.18.2, desenvolvido para suprir a falta de mods de homes para servidores modificados. O mod permite que jogadores criem até 3 homes e se teleportem para elas, com sistema anti-combat log integrado.

## 📋 Características

- Limite de 3 homes por jogador
- Sistema anti-combat log (não permite teleporte em combate)
- Delay de 3 segundos no teleporte
- Salvamento automático das homes em arquivo JSON
- Suporte a todas as dimensões do jogo
- Interface amigável com mensagens coloridas

## 🎮 Comandos

| Comando | Uso | Descrição |
|---------|-----|-----------|
| /sethome | `/sethome <nome>` | Define uma nova home na sua localização atual |
| /home | `/home <nome>` | Teleporta você para uma home específica |
| /delhome | `/delhome <nome>` | Remove uma home específica |
| /homes | `/homes` | Lista todas as suas homes com coordenadas |

## 🚦 Sistema de Mensagens

O mod utiliza um sistema de cores para facilitar a compreensão:

- **Verde** (§a): Sucesso nas operações
- **Amarelo** (§e): Informações e listagens
- **Vermelho** (§c): Erros e avisos
- **Dourado** (§6): Avisos de teleporte

## ⚙️ Instalação

1. Baixe o arquivo .jar mais recente
2. Coloque o arquivo na pasta `mods` do seu servidor/cliente
3. Inicie o servidor/cliente
4. As homes serão salvas automaticamente em `homes.json`

## 📌 Requisitos

- Minecraft 1.18.2
- Forge 40.2.0+
- Java 17

## 🤔 Por que este mod?

Este mod foi criado devido à falta de opções atualizadas de mods de homes para servidores Forge. A maioria das soluções disponíveis são plugins Bukkit/Spigot, que não funcionam em servidores Forge. O HomeMod oferece uma solução simples e eficiente para esta necessidade.

## 🔒 Sistema Anti-Combat Log

Para evitar abusos em PvP, o mod inclui:
- Bloqueio de teleporte durante combate
- Delay de 3 segundos antes do teleporte
- Cancelamento do teleporte se entrar em combate durante o delay

## 💾 Persistência de Dados

- As homes são salvas em um arquivo JSON
- Dados persistem após reiniciar o servidor
- Sistema baseado no nome do jogador
- Backup automático ao desligar o servidor

## 🛠️ Desenvolvimento

Desenvolvido usando:
- Forge MDK 1.18.2
- Gradle como sistema de build
- GSON para persistência de dados

## 📄 Licença

Todos os direitos reservados.

---

Desenvolvido por yleo para a comunidade Minecraft Forge.
