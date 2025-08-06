
INSERT INTO `sexe` (`CODE_SEXE`, `LIB_SEXE`) VALUES ('1', 'M'), ('2', 'F');

INSERT INTO `user_authentication` (`USER_ID`, `CODE_SEXE`, `USERNAME`, `PASSWORD`, `ENABLED`, `EMAIL`, `NOM`, `PRENOMS`, `PHONE1`, `PHONE2`, `PHOTO`, `LIEU_NAIS`, `DATE_NAIS`) VALUES
(1, 1, 'ADMIN', 'admin', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 1, 'lekerand', 'toujours', 1, 'lekerand@yahoo.fr', 'KOFFI', 'Alain George', '12536478', '12365478', NULL, 'Abidjan', '1970-10-06');

INSERT INTO `user_authorization` (`USER_ROLE_ID`, `USER_ID`, `ROLE`) VALUES
(1, 1, 'ROLE_ADMIN'),
(2, 2, 'ROLE_ADMIN');


INSERT INTO `annees_scolaire` (`CODE_ANNEES`, `ANNEES_DEBUT`, `ANNEES_FIN`, `SESSION_EXAMEN`, `DATE_COMMISSION`, `LIB_ANNEE_SCOLAIRE`, `ETAT_ANNE_SCOLAIRE`, `DATE_CREATION`, `DATE_CLOTURE`) VALUES ('1', '2018', '2019', 'Juillet 2019', '2019/07/17', '2018 / 2019', 'Ouverte', CURRENT_TIMESTAMP, '0000-00-00 00:00:00.000000');


INSERT INTO `tformation` (`CODE_TFORMATION`, `LIB_TFORMATION`, `ABREV_TFORMATION`) VALUES ('1', 'Licence Masteur Doctorat', 'LMD'), ('2', 'Formation Classique', 'CLASSIQUE');


INSERT INTO `typenationalite` (`CODE_TYPENATIONALITE`, `LIB_TYPENATIONALITE`) VALUES
(1, 'NATIONAUX'),
(2, 'NON NATIONAUX');

INSERT INTO `cycle`(`CODE_CYCLE`, `NOM_CYCLE`, `TAUX_HORAIRE`) VALUES ('1', 'Cycle moyen', '6000'), ('2', 'Cycle supérieur', '8000');

INSERT INTO `ecole` (`CODE_ECOLE`, `NOM_ECOLE`, `ABREV_ECOLE`) VALUES ('1', 'Ecole Supérieure de Navigation', 'ESN'), ('2', 'Ecole Supérieure des Transports Maritimes', 'ESTM');

INSERT INTO `domaine` (`CODE_DOMAINE`, `LIB_DOMAINE`) VALUES ('1', 'SCIENCES ECONOMIQUES ET DE GESTION');

INSERT INTO `filieres` (`CODE_FILIERE`, `CODE_ECOLE`, `CODE_TFORMATION`, `CODE_DOMAINE`, `NOM_FILIERE`, `ABREV_FILIERE`, `NOM_FILIERE2`, `ABEV2`) VALUES ('1', '2', '1', '1', 'Licence Professionnelle en Transport Maritime et Logistique', 'LPTML', 'Licence Professionnelle en Transport Maritime et Logistique', 'LP TML'), ('2', '2', '2', '1', 'Ingénieur en Management des Activités Maritimes et Portuaires', 'DIMAMP', 'Diplôme d\'Ingénieur en Management des Activités Maritimes et Portuaires', 'DIMAMP');

INSERT INTO `mention` (`CODE_MENTION`, `CODE_FILIERE`, `CODE_CYCLE`, `LIB_MENTION`, `ABREV_MENTION`, `NIVEAU_MENTION`, `ANNEE_MENTION`, `MTN_SCOLARITE`, `MTNN_SCOLARITE`, `MT_HORAIRE`) VALUES ('1', '1', '1', 'Licence Professionnelle 1ère Annéé', 'LP 1', 'Licence 1', '1ère Année', NULL, NULL, NULL), ('2', '1', '1', 'Licence Professionnelle 2 Transit Consignation Armement', 'LP 2 TCA', 'Licence 2', '2ème Année', NULL, NULL, NULL), ('3', '2', '2', 'Ingénieur en Management des Activités Maritimes et Portuaires 1ère Année', 'ING 1', '3ème Année', '3ème Année', NULL, NULL, NULL);

INSERT INTO `diplomes` (`CODE_DIPLOME`, `LIB_DIPLOME`, `ABREV_DIPLOME`) VALUES ('1', 'Baccalauréat Série D', 'BAC D'), ('2', 'Baccalauréat Série C', 'BAC C'), ('3', 'Baccalauréat Série A1', 'BAC A1'), ('4', 'Baccalauréat Série A2', 'BAC A2');

INSERT INTO `nationalites` (`CODENATIONALITE`, `LIBNATIONALITE`) VALUES ('1', 'Ivoirienne'), ('2', 'Camerounaise'), ('3', 'Congolaise'), ('4', 'Togolaise');

INSERT INTO `niveaux` (`CODENIVEAU`, `LIB_NIVEAU`, `ABREV_NIVEAU`) VALUES ('1', 'Terminale', 'TLE'), ('2', 'Universitaire', 'UV');

INSERT INTO `pays` (`CODEPAYS`, `LIBPAYS`, `REPUBLIC`, `ABREVPAYS`) VALUES ('1', 'Côte d\'Ivoire', 'République de Côte d\'Ivoire', 'RCI'), ('2', 'Cameroun', 'République du Camaroun', 'RC'), ('3', 'Congo - Brazzaville', 'République du Congo', 'RC'), ('4', 'Togo', 'République du Togo', 'RT');
INSERT INTO `regime` (`CODE_REGIME`, `LIB_REGIME`) VALUES ('1', 'Boursier'), ('2', 'Non Boursier');
INSERT INTO `santes` (`CODESANTE`, `LIBSANTE`) VALUES ('1', 'Bonne Santé'), ('2', 'Souvent malade');
INSERT INTO `specialite` (`CODE_SPECIAL`, `LIBELLE_SPECIAL`) VALUES ('1', 'Informatique');
INSERT INTO `type_logement` (`CODETYPE_LOGEMENT`, `LIBTYPE_LOGEMENT`, `MT_TYPE_LOGEMENT`, `MT_TYPE_LOGEMENT_CAUTION`) VALUES ('1', 'Dortoir', '80000.00', '30000.00'), ('2', 'Chambre', '130000.00', '30000.00'), ('3', 'Externat', '0.00', '0.00');
INSERT INTO `logement` (`CODE_LOGE`, `CODETYPE_LOGEMENT`, `LIB_CAMPUS_LOGE`, `LIB_DETAIL_CAMPUS_LOGE`, `ABREV_AMPUS_LOGE`) VALUES ('1', '2', 'Chambre 1', 'Chambre ordinaire', 'C 1');
INSERT INTO `matrimoniales` (`CODEMATRIMONIALE`, `LIBMATRIMONIALE`) VALUES ('1', 'Célibataire'), ('2', 'Marié (e)'), ('3', 'Divorcé (e)');