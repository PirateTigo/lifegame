## Игра "Жизнь"

Одна из множества реализаций [всем известной игры](https://ru.wikipedia.org/wiki/%D0%98%D0%B3%D1%80%D0%B0_%C2%AB%D0%96%D0%B8%D0%B7%D0%BD%D1%8C%C2%BB), использующая Java FX.

Код проекта вы можете найти [здесь](https://github.com/PirateTigo/lifegame).

### Правила игры
```markdown
- Место действия игры - "вселенная" - размеченная на клетки ограниченная плоскость.
- Каждая клетка на этой поверхности может находиться в двух состояниях: быть "живой" (заполненной) или быть "мертвой" (пустой). Клетка имеет восемь соседей, окружающих её.
- Распределение живых клеток в начале игры называется первым поколением. Каждое следующее поколение рассчитывается на основе предыдущего по таким правилам:
  - в пустой (мёртвой) клетке, рядом с которой ровно три живые клетки, зарождается жизнь;
  - если у живой клетки есть две или три живые соседки, то эта клетка продолжает жить;
  - в противном случае, если соседей меньше двух или больше трёх, клетка умирает ("от одиночества" или "от перенаселенности")

```

```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```

For more details see [Basic writing and formatting syntax](https://docs.github.com/en/github/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax).

### Jekyll Themes

Your Pages site will use the layout and styles from the Jekyll theme you have selected in your [repository settings](https://github.com/PirateTigo/lifegame/settings/pages). The name of this theme is saved in the Jekyll `_config.yml` configuration file.

### Support or Contact

Having trouble with Pages? Check out our [documentation](https://docs.github.com/categories/github-pages-basics/) or [contact support](https://support.github.com/contact) and we’ll help you sort it out.
