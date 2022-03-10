# dbHWs

### Аггрегирование без индексов (посмотрим, какие отзывы оставили первые n пользователей):
<br>
Сам запрос без индексов:
<br>
<code>
db.users.aggregate( [<br>
   {$limit:1000},<br>
   {<br>
      $lookup:<br>
         {<br>
           from: "ratings",<br>
           let: { userid_in_ratings: "$userid" },<br>
            pipeline: [<br>
               { $match:<br>
                  { $expr:<br>
                     { $and:<br>
                        [<br>
                          { $eq: [ "$userid",  "$$userid_in_ratings" ] }<br>
                        ]<br>
                     }<br>
                  }<br>
               }<br>
            ],<br>
            as: "rating_info"<br>
          }<br>
    }<br>
] ).explain("executionStats")<br>
</code>
<br>
Фрагмент ответа:<br>
<img src="./photo/q1.png" />
Время выполнения:<br>
executionTimeMillis: 608716
<br>

### Индексирование:
<img src="./photo/in1.png" />
<img src="./photo/in2.png" />
Время выполнения после индексирования:<br>
executionTimeMillis: 406<br>
Видим значительный прирост производительности после индексирования поля, по которому производим join в аггрегации

### Update
Сам запрос:<br>
<code>
db.ratings.update(<br>
  {},<br>
  [{ $set: { str1: { $concat: [ "$str1", "1" ] } } }],<br>
  { multi: true }<br>
)<br>
</code>
<br>
Фрагмент ответа:<br>
<img src="./photo/q2.png" />
<br>
Время выполнения:<br>
executionTimeMillis: 4336<br>

Время выполнения после индексирования:<br>
 executionTimeMillis: 4337<br>
 Индексирование не ускорило апдейт всех полей, что логично и ожидаемо
