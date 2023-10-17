---
name: User Story
about: Describe the User Story
title: "[US]"
labels: bug, US
assignees: ''

---

:star: **Titre/Title :** En quelques mots, résumez l'objectif de l'US par exemple _Ajout d'un produit dans le panier_.

:star: **Description :**

**As a** [type of user], **I want** [an action] **so that** [a benefit/a value]<br>
**En tant que** [type of user], **Je veux** [an action] **afin de** [a benefit/a value]


:star2: **Priorité/Priority :** 

Proposition d'échelle, la [méthode MoSCoW](https://paper-leaf.com/insights/prioritize-user-stories/)
  1. _**Must have/Doit avoir :** la première version de ce produit nécessite absolument cette fonctionnalité - elle est essentielle au succès du produit._
  2. _**Should Have/Devrait avoir** : l'idéal serait que la première version de ce produit dispose de cette fonctionnalité, mais elle n'est pas absolument nécessaire. Elles peuvent être aussi importantes, mais pas aussi critiques en termes de temps, que les "Must Have"._
  3. _**Could have/Aurait pu**: l'histoire de l'utilisateur a de la valeur et est souhaitable, mais en fin de compte, elle n'est pas nécessaire._
  4. _**Won't have/N'aura pas** : l'histoire de l'utilisateur est considérée comme étant parmi les moins critiques ou les moins utiles._
  
  
:star2: **Estimation/Estimate :** 


:sparkles: **Règle métier /Business rules :**

```
lorsque je rajoute un élément supplémentaire d'un produit dans mon 
panier.
    - si quantité > stock alors erreur "pas assez de stock 
      disponible"
    - si quantité < stock alors on ajoute +1 à la quantité_
```
  
:star2: **Critère d'acceptation/Acceptance criteria**
Précisez l'ensemble des conditions que la story doit satisfaire pour être considérée comme complète et terminée.
  
_Voici quelques exemples_<br>
_**Scénario:**_
```
     _Etant donné_ que je suis sur mon panier 
     _Et que_ j'ai un produit d'id "1234" en quantité "1"
     _Et que_ le stock restant sur ce produit est de "0"
     _Quand_ j'ajoute "1" quantité sur mon produit
     _Alors_ mon panier affichera une erreur
```

_**Scénario:**_<br>
```
    Etant donné que je suis sur mon panier 
    Et que j'ai un produit d'id "1235" en quantité "1" <br>
    Et que le stock restant sur ce produit est de "10"
    Quand j'ajoute "1" quantité de mon produit
    Alors mon produit aura "2" quantités
```
## Legende 
- :star:  Requis 
- :star2: Requis avant le passage en développement.
- :sparkles: Un plus

> Ce texte et ces exemples s'inspirent de  : https://blog.myagilepartner.fr/index.php/2017/03/18/story-a4/
