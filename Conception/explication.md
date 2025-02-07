
la partie structure donc tuple, tabledata, tableschema (je pense au interface ou abstraite), les database, les indexs, les contraintes (je sais pas trop comment tout structurer pour les contraintes et tout), les logs, et une sorte de version temporaire sur lesquel on fait les modif et si elle respecte toutes les contrainte et l'intégriter général alors on remplace l'ancienne verion par les nouvelles

la partie qui gere le stockage donc:
serializer pour transcire ce qu'il y a dans la ram sur le disque, et un parseur pour lire le disque et tout retransformer en objets
un system de buffers pour la structure des tables, les datas des tables, les index, les contraintes (donc gestion de la ram)

et ensuite la partie language (sa je connais moin bien les termes) :
donc un parser pour transformer la transaction en token et ensuite organiser tout pour que sa passe dans le bonne ordre puis un éxécuteur pour faire les appels qui convienne pour faire se qui est demander dans les requettes de la transaction

il faudrais aussi un system qui permet de géré le fonctionnement de la bd par exemple :
un schema de table a des collones qui ont un nom, type de donnée, est ce que c'est nullable, valeur par défaut