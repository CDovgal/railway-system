-------------------------------------------------------------
SELECT characteristic_name,
  value
FROM
  (SELECT characteristic_type AS join3,
    value
  FROM
    (SELECT carriage_type.carriage_type_id AS join2
    FROM carriage
    JOIN carriage_type
    ON carriage_type.carriage_type_id = carriage.fk_carriage_type_id
    WHERE carriage.carriage_id        = 3
    )
  JOIN
    (SELECT characteristic.characteristic_type,
      characteristic.value,
      characteristic.fk_carriage_type_id AS join1
    FROM characteristic
    JOIN carriage_type
    ON characteristic.fk_carriage_type_id = carriage_type.carriage_type_id
    ) ON join2                            = join1
  )
JOIN characteristic_type
ON characteristic_type.characteristic_type_id = join3;
------------------------------------------------------------
SELECT carriage_id, carriage_type.carriage_type_name FROM carriage
JOIN carriage_type 
ON carriage.fk_carriage_type_id = carriage_type.carriage_type_id
WHERE carriage.fk_rolling_stock_id = 2;
---------------------CURSOR--------------------------------
SET SERVEROUTPUT ON
DECLARE
  v_carriage carriage.carriage_id%TYPE;
  v_carriage_type carriage_type.carriage_type_name%TYPE;
  v_stock_id carriage.fk_rolling_stock_id%TYPE;
  CURSOR c_i_for_r_s(stock_id carriage.fk_rolling_stock_id%TYPE)
  IS
    SELECT carriage_id,
      carriage_type.carriage_type_name
    FROM carriage
    JOIN carriage_type
    ON carriage.fk_carriage_type_id    = carriage_type.carriage_type_id
    WHERE carriage.fk_rolling_stock_id = stock_id;
BEGIN
  v_stock_id := 2;
  OPEN c_i_for_r_s(v_stock_id);
  DBMS_OUTPUT.enable;
  LOOP
    FETCH c_i_for_r_s INTO v_carriage, v_carriage_type;
    EXIT WHEN(c_i_for_r_s%NOTFOUND);
    DBMS_OUTPUT.put_line(TO_CHAR(v_carriage)||' '||v_carriage_type);
  END LOOP;
  CLOSE c_i_for_r_s;
END;
------------------------------------------------------------