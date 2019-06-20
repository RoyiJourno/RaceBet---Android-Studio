import httplib2
import flask
from flask import request, jsonify
from sqlalchemy import create_engine, MetaData, Table, Column, ForeignKey, and_, text
from sqlalchemy.orm import sessionmaker, scoped_session
from sqlalchemy.ext.automap import automap_base
import json


# Global http obj
h = httplib2.Http()
# Query serializers
def group_serializer(group):
    return dict(gid=group.gid, gname=group.gname, maxusers=group.maxusers, duration=str(group.duration),
                betprice=group.betprice, adminid=group.adminid)
def user_serializer(user):
    return dict(id=user.id, name=user.name, phone=user.phone, email=user.email,
                ppath=user.ppath)
def payment_serializer(payment):
    return dict(amount=payment.amount, out=payment.out, madeby=payment.madeby, forgroup=payment.forgroup)

# Get user's email from id_token header and check for token integrity

def auth_user(jwt):
    # The below endpoint will return JSON formatted =>>>
    # =>>>id_token claims (such as email) after succesfull validation
    if h.request('https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=' + jwt, method="GET",
                             headers={'accept': 'application/json'})[0] == 200:
        return jwt.decode(jwt)



engine = create_engine("somthing.something")
connection = engine.connect()

Session = sessionmaker(bind=engine)


metadata = MetaData()
Base = automap_base()
Base.prepare(engine, reflect=True)

Users = Base.classes.Users
Groups = Base.classes.Groups
Payments = Base.classes.Payments
Users_Groups = Base.classes.usergroup

app = flask.Flask(__name__)

# Add group

@app.route('/group', methods=['POST', 'PUT', 'GET'])
def post_group():
    try:
        if request.method == 'POST':
            group_to_insert = Groups()
            group_to_insert.gid = request.form['id']
            group_to_insert.gname = request.form['name']
            group_to_insert.duration = request.form['duration']
            group_to_insert.maxusers = int(request.form['max_users'])
            group_to_insert.betprice = int(request.form['bet_price'])
            group_to_insert.adminid = request.form['admin_id']
            db_session = scoped_session(sessionmaker(bind=engine))
            db_session.add(group_to_insert)
            db_session.commit()
            db_session.remove()
            return 200
        elif request.method == 'PUT':
            db_session = scoped_session(sessionmaker(bind=engine))
            id = request.form['id']
            upname = request.form['name']
            db_session.query(Groups).filter(Groups.gid == id).update({'gname': upname})
            db_session.commit()
            db_session.remove()
            return 200
        else:
            db_session = scoped_session(sessionmaker(bind=engine))
            id = request.form['id']
            q = db_session.query(Groups).filter(Groups.gid == id).first()
            res = group_serializer(q)
            db_session.remove()
            return json.dumps(res), 200
    except KeyError:
        return "Bad request", 400
# Group amendment



# Add user

@app.route('/user', methods=['POST', 'PUT', 'GET'])
def user():
    try:
        if request.method == 'POST':
            user_to_insert = Users()
            user_to_insert.id = request.form['id']
            user_to_insert.name = request.form['name']
            user_to_insert.phone = request.form['phone']
            user_to_insert.ppath = request.form['ppath']
            user_to_insert.email = request.form['email']
            db_session = scoped_session(sessionmaker(bind=engine))
            db_session.add(user_to_insert)
            db_session.commit()
            db_session.remove()
            return 200

        elif request.method == 'PUT':
                db_session = scoped_session(sessionmaker(bind=engine))
                id = request.form['id']
                if 'name' in request.form:
                    upname = request.form['name']
                    db_session.query(Users).filter(Users.gid == id).update({'name': upname})
                if 'ppath' in request.form:
                    ppath = request.form['ppath']
                    db_session.query(Users).filter(Users.gid == id).update({'ppath': ppath})
                db_session.commit()
                db_session.remove()
                return 200

        else:
            db_session = scoped_session(sessionmaker(bind=engine))
            id = request.form['id']
            query = db_session.query(Users).filter(Users.gid == id).first()
            res = user_serializer(query)
            return json.dumps(res), 200
    except KeyError:
        return "Bad request", 400

# Post will add user to group
# Get will return list of groups that user is assinged to

@app.route('/groupofusers', methods=['GET','POST'])
def usertogroup():
    try:
        if request.method == 'POST':
            usergroup_add = Users_Groups()
            db_session = scoped_session(sessionmaker(bind=engine))
            user_id= request.form['id']
            group_id = request.form['id']
            usergroup_add.id = user_id
            usergroup_add.id = group_id
            db_session.add(usergroup_add)
            db_session.commit()
            db_session.remove()
            return 200
        else:
            id = request.form['id']
            query = Groups.query.join(Users).join(Groups).filter(Users.gid == id).all()
            list_to_return = []
            for item in query:
                list_to_return.append(group_serializer(item))
            return json.dumps(list_to_return), 200
    except KeyError:
        return "Bad request", 400

# GET all users of a group
@app.route('/usersingroup', methods=['GET'])
def grouptousers():
    try:
            gid = request.form['gid']
            query = Users.query.join(Users).join(Groups).filter(Users.gid == id).all()
            list_to_return = []
            for item in query:
                list_to_return.append(user_serializer(item))
            return json.dumps(list_to_return), 200
    except KeyError:
        return "Bad request", 400




app.run()
